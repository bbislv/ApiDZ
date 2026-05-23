package com.example.ApiDZ.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CacheManagementService {

    private final Cache<String, Long> userProductCache;
    private final Cache<String, Long> transactionSumCache;
    private final Cache<String, Boolean> depositWithdrawCompareCache;

    public CacheManagementService(
            @Qualifier("userProductCache") Cache<String, Long> userProductCache,
            @Qualifier("transactionSumCache") Cache<String, Long> transactionSumCache,
            @Qualifier("depositWithdrawCompareCache") Cache<String, Boolean> depositWithdrawCompareCache) {
        this.userProductCache = userProductCache;
        this.transactionSumCache = transactionSumCache;
        this.depositWithdrawCompareCache = depositWithdrawCompareCache;
    }

    public void clearAllCaches() {
        userProductCache.invalidateAll();
        transactionSumCache.invalidateAll();
        depositWithdrawCompareCache.invalidateAll();
    }
}
