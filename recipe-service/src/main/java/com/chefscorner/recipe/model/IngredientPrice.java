package com.chefscorner.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientPrice {
    private Integer id;
    private String seller;
    private Double price;
    private String owner;
}
