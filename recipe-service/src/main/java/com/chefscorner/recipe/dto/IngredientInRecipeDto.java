package com.chefscorner.recipe.dto;

import com.chefscorner.recipe.model.IngredientInRecipe;
import com.chefscorner.recipe.model.IngredientPrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class IngredientInRecipeDto {

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

    public static IngredientInRecipeDto from(IngredientInRecipe ingredient){
        return IngredientInRecipeDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .price_per_unit(ingredient.getPrice_per_unit())
                .image(ingredient.getImage())
                .svg(ingredient.getSvg())
                .amount(ingredient.getAmount())
                .description(ingredient.getDescription())
                .unit(ingredient.getUnit())
                .grams(ingredient.getGrams())
                .prices(ingredient.getPrices())
                .build();
    }
}
