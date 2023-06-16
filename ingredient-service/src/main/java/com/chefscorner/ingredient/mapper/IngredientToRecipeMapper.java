package com.chefscorner.ingredient.mapper;

import com.chefscorner.ingredient.dto.IngredientDto;
import com.chefscorner.ingredient.dto.IngredientToRecipeDto;
import com.chefscorner.ingredient.model.IngredientToRecipe;

import java.util.stream.Collectors;

public class IngredientToRecipeMapper {

    public static IngredientToRecipeDto ingredientToRecipeToIngredientToRecipeDto(IngredientToRecipe ingredient){
        IngredientDto ingredientDto = IngredientMapper.ingredientToIngredientDtoWithImages(ingredient.getIngredient());
        return IngredientToRecipeDto.builder()
                .id(ingredientDto.getId())
                .name(ingredientDto.getName())
                .price_per_unit(ingredientDto.getPrice_per_unit())
                .image(ingredientDto.getImage())
                .svg(ingredientDto.getSvg())
                .amount(ingredient.getAmount())
                .description(ingredient.getDescription())
                .unit(ingredient.getUnit())
                .grams(ingredient.getGrams())
                .prices(ingredient.getIngredient().getIngredientPrices().stream()
                        .map(IngredientMapper::priceToPriceDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
