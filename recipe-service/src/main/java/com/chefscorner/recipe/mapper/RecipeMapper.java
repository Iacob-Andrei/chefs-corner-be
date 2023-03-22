package com.chefscorner.recipe.mapper;

import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.model.IngredientToRecipe;
import com.chefscorner.recipe.model.Recipe;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper {

    public static RecipeDto recipeToRecipeDto(Recipe recipe, List<IngredientToRecipe> ingredients) {
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
                .ingredients(ingredients.stream()
                            .map(IngredientToRecipeMapper::ingredientToRecipeToIngredientToRecipeDto)
                            .collect(Collectors.toList()))
                .categories(recipe.getCategories().stream()
                            .map(CategoryMapper::categoryToCategoryDto)
                            .collect(Collectors.toList()))
                .build();
    }

    public static RecipeDto recipeToRecipeDtoOnlyInfo(Recipe recipe){
        return RecipeDto.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .image(recipe.getImage())
                .build();
    }
}
