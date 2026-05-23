package com.example.ApiDZ.repository.rules;

import com.example.ApiDZ.domain.rules.RuleTriggerStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional("rulesTransactionManager")
public interface RuleTriggerStatRepository extends JpaRepository<RuleTriggerStat, UUID> {
}
