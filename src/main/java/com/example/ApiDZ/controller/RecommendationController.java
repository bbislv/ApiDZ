package com.example.ApiDZ.controller;

import com.example.ApiDZ.domain.rules.RuleEntity;
import com.example.ApiDZ.repository.rules.RuleRepository;
import com.example.ApiDZ.service.RuleEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {
    private final RuleRepository ruleRepo;
    private final RuleEvaluationService evaluationService;

    @GetMapping("/{userId}")
    public List<String> getRecommendations(@PathVariable String userId) {
        List<String> recommendations = new ArrayList<>();

        if (checkOldRule(userId)) {
            recommendations.add("Старый продукт");
        }

        List<RuleEntity> rules = ruleRepo.findAll();
        for (RuleEntity rule : rules) {
            if (evaluationService.evaluate(userId, rule.getRuleJson())) {
                recommendations.add(rule.getProductText());
            }
        }
        return recommendations;
    }

    private boolean checkOldRule(String userId) {
        return false;
    }
}
