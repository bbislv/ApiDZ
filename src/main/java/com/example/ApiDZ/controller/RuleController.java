package com.example.ApiDZ.controller;

import com.example.ApiDZ.domain.rules.RuleEntity;
import com.example.ApiDZ.dto.RuleRequestDto;
import com.example.ApiDZ.repository.rules.RuleRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rule")
@RequiredArgsConstructor
public class RuleController {
    private final RuleRepository ruleRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping
    public ResponseEntity<Map<String, Object>> createRule(@RequestBody RuleRequestDto dto) {
        try {
            String ruleJson = objectMapper.writeValueAsString(dto.getRule());
            RuleEntity entity = new RuleEntity(
                    null,
                    dto.getProduct_name(),
                    dto.getProduct_id(),
                    dto.getProduct_text(),
                    ruleJson
            );
            RuleEntity saved = ruleRepo.save(entity);

            return ResponseEntity.ok(Map.of(
                    "id", saved.getId().toString(),
                    "product_name", saved.getProductName(),
                    "product_id", saved.getProductId(),
                    "product_text", saved.getProductText(),
                    "rule", objectMapper.readValue(saved.getRuleJson(), Object.class)
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid request: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllRules() {
        List<Map<String, Object>> rules = ruleRepo.findAll().stream()
                .<Map<String, Object>>map(r -> {
                    try {
                        Object ruleData = objectMapper.readValue(r.getRuleJson(), Object.class);
                        return Map.of(
                                "id", r.getId().toString(),
                                "product_name", r.getProductName(),
                                "product_id", r.getProductId(),
                                "product_text", r.getProductText(),
                                "rule", ruleData
                        );
                    } catch (Exception e) {
                        throw new RuntimeException("Ошибка парсинга правила", e);
                    }
                })
                .toList();

        return ResponseEntity.ok(Map.of("data", rules));
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRule(@PathVariable String productId) {
        ruleRepo.deleteByProductId(productId);
    }
}