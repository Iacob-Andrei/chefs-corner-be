package com.chefscorner.recipe.mapper;

import com.chefscorner.recipe.dto.IngredientDto;
import com.chefscorner.recipe.model.Ingredient;

public class IngredientMapper {

    public static IngredientDto ingredientToIngredientDtoWithImages(Ingredient ingredient){
        return IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .image(ingredient.getImage())
                .svg(ingredient.getSvg())
                .build();
    }
}
