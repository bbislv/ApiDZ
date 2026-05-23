package com.example.ApiDZ.repository.rules;

import com.example.ApiDZ.domain.rules.RuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional("rulesTransactionManager")
public interface RuleRepository extends JpaRepository<RuleEntity, UUID> {

    Optional<RuleEntity> findByProductId(String productId);

    void deleteByProductId(String productId);
}
