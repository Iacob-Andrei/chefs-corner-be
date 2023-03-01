package com.chefscorner.recipe.mapper;

import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.model.Recipe;

import java.util.stream.Collectors;

public class RecipeMapper {

    public static RecipeDto recipeToRecipeDto(Recipe recipe) {
        return RecipeDto.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .prep_time(recipe.getPrep_time())
                .cook_time(recipe.getCook_time())
                .number_servings(recipe.getNumber_servings())
                .image(recipe.getImage())
                .directions(recipe.getDirections().stream()
                            .map(DirectionMapper::directionToDirectionDto)
                            .collect(Collectors.toList()))
                .build();
    }
}
