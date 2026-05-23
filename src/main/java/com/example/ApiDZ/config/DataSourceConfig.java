package com.example.ApiDZ.config;

import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String mainUrl;
    @Value("${spring.datasource.driver-class-name}")
    private String mainDriver;
    @Value("${spring.datasource.username:sa}")
    private String mainUser;
    @Value("${spring.datasource.password:}")
    private String mainPass;

    @Value("${spring.datasource-rules.url}")
    private String rulesUrl;
    @Value("${spring.datasource-rules.driver-class-name}")
    private String rulesDriver;
    @Value("${spring.datasource-rules.username}")
    private String rulesUser;
    @Value("${spring.datasource-rules.password}")
    private String rulesPass;

    @Primary
    @Bean(name = "mainDataSource")
    public DataSource mainDataSource() {
        return DataSourceBuilder.create()
                .url(mainUrl)
                .driverClassName(mainDriver)
                .username(mainUser)
                .password(mainPass)
                .build();
    }

    @Bean(name = "rulesDataSource")
    public DataSource rulesDataSource() {
        return DataSourceBuilder.create()
                .url(rulesUrl)
                .driverClassName(rulesDriver)
                .username(rulesUser)
                .password(rulesPass)
                .build();
    }

    @Primary
    @Bean(name = "mainEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(
            @Qualifier("mainDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.ApiDZ.domain.main");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "update");
        em.setJpaPropertyMap(props);
        return em;
    }

    @Bean(name = "rulesEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean rulesEntityManagerFactory(
            @Qualifier("rulesDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.ApiDZ.domain.rules");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "none");
        em.setJpaPropertyMap(props);
        return em;
    }

    @Primary
    @Bean(name = "mainTransactionManager")
    public PlatformTransactionManager mainTransactionManager(
            @Qualifier("mainEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean(name = "rulesTransactionManager")
    public PlatformTransactionManager rulesTransactionManager(
            @Qualifier("rulesEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public SpringLiquibase liquibase(@Qualifier("rulesDataSource") DataSource rulesDataSource,
                                     @Value("${spring.liquibase.change-log}") String changeLog) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(rulesDataSource);
        liquibase.setChangeLog(changeLog);
        liquibase.setShouldRun(true);
        return liquibase;
    }
}