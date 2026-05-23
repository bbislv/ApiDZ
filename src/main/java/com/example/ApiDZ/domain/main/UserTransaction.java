package com.example.ApiDZ.domain.main;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String productType;

    @Column(nullable = false)
    private String transactionType;

    @Column(nullable = false)
    private Long amount;
}
