package com.example.ApiDZ.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {
    @Value("${cache.caffeine.spec}")
    private String cacheSpec;

    @Bean("userProductCache")
    public Cache<String, Long> userProductCache() {
        return Caffeine.from(cacheSpec).build();
    }

    @Bean("transactionSumCache")
    public Cache<String, Long> transactionSumCache() {
        return Caffeine.from(cacheSpec).build();
    }

    @Bean("depositWithdrawCompareCache")
    public Cache<String, Boolean> depositWithdrawCompareCache() {
        return Caffeine.from(cacheSpec).build();
    }
}
