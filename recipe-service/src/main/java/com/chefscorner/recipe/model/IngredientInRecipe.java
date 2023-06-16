package com.chefscorner.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientInRecipe {
    private Integer id;
    private String name;
    private Float price_per_unit;
    private String image;
    private String svg;
    private Double amount;
    private String description;
    private String unit;
    private Double grams;
    private List<IngredientPrice> prices;
}