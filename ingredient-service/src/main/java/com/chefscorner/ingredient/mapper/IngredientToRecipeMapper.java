package com.chefscorner.ingredient.mapper;

import com.chefscorner.ingredient.dto.IngredientToRecipeDto;
import com.chefscorner.ingredient.model.IngredientToRecipe;

public class IngredientToRecipeMapper {

    public static IngredientToRecipeDto ingredientToRecipeToIngredientToRecipeDto(IngredientToRecipe ingredient){
        return IngredientToRecipeDto.builder()
                .id(ingredient.getId())
                .ingredient(IngredientMapper.ingredientToIngredientDtoWithImages(ingredient.getIngredient()))
                .amount(ingredient.getAmount())
                .description(ingredient.getDescription())
                .unit(ingredient.getUnit())
                .grams(ingredient.getGrams())
                .build();
    }
}
