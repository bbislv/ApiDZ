package com.example.ApiDZ.domain.rules;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_id", nullable = false, unique = true)
    private String productId;

    @Column(name = "product_text", nullable = false, columnDefinition = "TEXT")
    private String productText;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "rule", nullable = false, columnDefinition = "jsonb")
    private String ruleJson;
}
