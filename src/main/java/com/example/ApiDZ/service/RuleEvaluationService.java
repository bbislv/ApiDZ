package com.example.ApiDZ.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RuleEvaluationService {
    private final UserKnowledgeService knowledgeService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public boolean evaluate(String userId, String ruleJson) {
        try {
            List<Map<String, Object>> queries = objectMapper.readValue(ruleJson, new TypeReference<>() {
            });
            for (Map<String, Object> q : queries) {
                String queryType = (String) q.get("query");
                List<String> args = (List<String>) q.get("arguments");
                boolean negate = (Boolean) q.get("negate");

                boolean result = executeQuery(userId, queryType, args);
                if (negate) result = !result;
                if (!result) return false;
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка оценки правила", e);
        }
    }

    private boolean executeQuery(String userId, String query, List<String> args) {
        return switch (query) {
            case "USER_OF" -> knowledgeService.getTransactionCount(userId, args.get(0)) > 0;
            case "ACTIVE_USER_OF" -> knowledgeService.getTransactionCount(userId, args.get(0)) >= 5;
            case "TRANSACTION_SUM_COMPARE" -> {
                long sum = knowledgeService.getSumAmount(userId, args.get(0), args.get(1));
                long constant = Long.parseLong(args.get(3));
                yield compare(sum, constant, args.get(2));
            }
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" -> {
                boolean depGreater = knowledgeService.compareDepositVsWithdraw(userId, args.get(0));
                yield compareBooleans(depGreater, args.get(1));
            }
            default -> throw new IllegalArgumentException("Неизвестный запрос: " + query);
        };
    }

    private boolean compare(long a, long b, String op) {
        return switch (op) {
            case ">" -> a > b;
            case "<" -> a < b;
            case "=" -> a == b;
            case ">=" -> a >= b;
            case "<=" -> a <= b;
            default -> false;
        };
    }

    private boolean compareBooleans(boolean aGreater, String op) {
        if (">".equals(op)) return aGreater;
        if ("<".equals(op)) return !aGreater;
        if ("=".equals(op)) return aGreater == (op.equals(">"));
        return false;
    }
}
