package com.chefscorner.ingredient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class IngredientToRecipeDto {

    private Integer id;
    private String name;
    private Float price_per_unit;
    private String image;
    private String svg;
    private Double amount;
    private String description;
    private String unit;
    private Double grams;
    private List<IngredientPriceDto> prices;
}
