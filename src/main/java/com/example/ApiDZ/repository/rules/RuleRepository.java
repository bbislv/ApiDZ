package com.example.ApiDZ.repository.rules;

import com.example.ApiDZ.domain.rules.RuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional("rulesTransactionManager")
public interface RuleRepository extends JpaRepository<RuleEntity, String> {
    void deleteByProductId(String productId);
}
