package com.chefscorner.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class IngredientToRecipeDto {

    private Integer id;
    private IngredientDto ingredient;
    private Double amount;
    private String description;
    private String unit;
    private Double grams;
}
