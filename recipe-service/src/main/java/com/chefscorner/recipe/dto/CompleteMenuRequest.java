package com.chefscorner.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CompleteMenuRequest {
    private Integer idMenu;
    private String name;
    private String description;
    private Map<String, Integer> requested;
}
