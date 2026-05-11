package com.example.ApiDZ.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleRequestDto {
    private String product_name;
    private String product_id;
    private String product_text;
    private List<RuleQueryDto> rule;
}