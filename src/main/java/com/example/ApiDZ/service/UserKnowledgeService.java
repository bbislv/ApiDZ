package com.example.ApiDZ.service;

import com.example.ApiDZ.repository.main.UserTransactionRepository;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserKnowledgeService {
    private final UserTransactionRepository repo;
    private final Cache<String, Long> userProductCache;
    private final Cache<String, Long> transactionSumCache;
    private final Cache<String, Boolean> depositWithdrawCompareCache;

    public long getTransactionCount(String userId, String productType) {
        String key = userId + "::" + productType;
        return userProductCache.get(key, k -> repo.countByUserIdAndProductType(userId, productType));
    }

    public long getSumAmount(String userId, String productType, String txType) {
        String key = userId + "::" + productType + "::" + txType;
        return transactionSumCache.get(key, k -> repo.sumAmountByUserProductTxType(userId, productType, txType));
    }

    public boolean compareDepositVsWithdraw(String userId, String productType) {
        String key = userId + "::" + productType;
        return depositWithdrawCompareCache.get(key, k -> {
            long dep = repo.sumAmountByUserProductTxType(userId, productType, "DEPOSIT");
            long wit = repo.sumAmountByUserProductTxType(userId, productType, "WITHDRAW");
            return dep > wit;
        });
    }
}
