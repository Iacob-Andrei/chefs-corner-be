package com.chefscorner.recipe.mapper;

import com.chefscorner.recipe.dto.IngredientToRecipeDto;
import com.chefscorner.recipe.model.IngredientToRecipe;

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
