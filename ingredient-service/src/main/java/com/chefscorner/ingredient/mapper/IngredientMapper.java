package com.chefscorner.ingredient.mapper;

import com.chefscorner.ingredient.dto.IngredientDto;
import com.chefscorner.ingredient.model.Ingredient;

public class IngredientMapper {

    public static IngredientDto ingredientToIngredientDtoWithImages(Ingredient ingredient){
        return IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .price_per_unit(ingredient.getPrice_per_unit())
                .image(ingredient.getImage())
                .svg(ingredient.getSvg())
                .build();
    }
}
