package com.example.ApiDZ.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.ApiDZ.repository.rules",
        entityManagerFactoryRef = "rulesEntityManagerFactory",
        transactionManagerRef = "rulesTransactionManager"
)
@EnableTransactionManagement
public class RulesJpaConfig {
}