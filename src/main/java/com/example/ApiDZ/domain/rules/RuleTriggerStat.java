package com.example.ApiDZ.domain.rules;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "rule_trigger_stats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleTriggerStat {

    @Id
    @Column(name = "rule_id")
    private UUID ruleId;

    @Column(nullable = false)
    private long count;
}
