package com.example.ApiDZ.service;

import com.example.ApiDZ.domain.rules.RuleTriggerStat;
import com.example.ApiDZ.repository.rules.RuleRepository;
import com.example.ApiDZ.repository.rules.RuleTriggerStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RuleTriggerStatService {

    private final RuleTriggerStatRepository statRepository;
    private final RuleRepository ruleRepository;

    @Transactional("rulesTransactionManager")
    public void increment(UUID ruleId) {
        RuleTriggerStat stat = statRepository.findById(ruleId)
                .orElse(new RuleTriggerStat(ruleId, 0L));
        stat.setCount(stat.getCount() + 1);
        statRepository.save(stat);
    }

    @Transactional("rulesTransactionManager")
    public void deleteByRuleId(UUID ruleId) {
        statRepository.deleteById(ruleId);
    }

    @Transactional(value = "rulesTransactionManager", readOnly = true)
    public List<Map<String, Object>> getAllStats() {
        Map<UUID, Long> counts = new HashMap<>();
        statRepository.findAll().forEach(stat -> counts.put(stat.getRuleId(), stat.getCount()));

        return ruleRepository.findAll().stream()
                .map(rule -> Map.<String, Object>of(
                        "rule_id", rule.getId().toString(),
                        "count", counts.getOrDefault(rule.getId(), 0L)
                ))
                .toList();
    }
}
