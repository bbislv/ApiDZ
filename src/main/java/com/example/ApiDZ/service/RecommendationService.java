package com.example.ApiDZ.service;

import com.example.ApiDZ.domain.rules.RuleEntity;
import com.example.ApiDZ.repository.rules.RuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RuleRepository ruleRepository;
    private final RuleEvaluationService evaluationService;
    private final RuleTriggerStatService triggerStatService;

    public List<String> getRecommendations(String userId) {
        List<String> recommendations = new ArrayList<>();

        if (checkOldRule(userId)) {
            recommendations.add("Старый продукт");
        }

        List<RuleEntity> rules = ruleRepository.findAll();
        for (RuleEntity rule : rules) {
            if (evaluationService.evaluate(userId, rule.getRuleJson())) {
                triggerStatService.increment(rule.getId());
                recommendations.add(rule.getProductText());
            }
        }
        return recommendations;
    }

    private boolean checkOldRule(String userId) {
        return false;
    }
}
