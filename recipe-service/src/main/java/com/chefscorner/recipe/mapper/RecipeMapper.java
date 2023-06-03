package com.chefscorner.recipe.mapper;

import com.chefscorner.recipe.dto.IngredientInRecipeDto;
import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.model.Category;
import com.chefscorner.recipe.model.IngredientInRecipe;
import com.chefscorner.recipe.model.Recipe;
import com.chefscorner.recipe.service.WebService;

import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper {

    public static RecipeDto recipeToRecipeDto(Recipe recipe, List<IngredientInRecipe> ingredients, WebService webService) {
        return RecipeDto.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .prep_time(recipe.getPrep_time())
                .cook_time(recipe.getCook_time())
                .number_servings(recipe.getNumber_servings())
                .image(recipe.getImage())
                .owner(recipe.getOwner())
                .file(recipe.getOwner().equals("public") ? new byte[0] : webService.getFile(recipe.getImage()))
                .directions(recipe.getDirections().stream()
                            .map(direction -> DirectionMapper.directionToDirectionDto(direction, direction.getVideo().equals("") ? new byte[0] : webService.getFile(direction.getVideo())))
                            .collect(Collectors.toList()))
                .ingredients(ingredients.stream()
                            .map(IngredientInRecipeDto::from)
                            .collect(Collectors.toList()))
                .categories(recipe.getCategories().stream()
                            .map(Category::getCategory)
                            .collect(Collectors.toList()))
                .build();
    }

    public static RecipeDto recipeToRecipeDtoOnlyInfo(Recipe recipe, byte[] image){
        return RecipeDto.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .prep_time(recipe.getPrep_time())
                .cook_time(recipe.getCook_time())
                .owner(recipe.getOwner())
                .image(recipe.getImage())
                .file(image)
                .build();
    }

    public static RecipeDto recipeToRecipeDtoWithoutDirections(Recipe recipe, List<IngredientInRecipe> ingredients, byte[] image){
        return RecipeDto.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .prep_time(recipe.getPrep_time())
                .cook_time(recipe.getCook_time())
                .number_servings(recipe.getNumber_servings())
                .image(recipe.getImage())
                .owner(recipe.getOwner())
                .file(image)
                .ingredients(ingredients.stream()
                        .map(IngredientInRecipeDto::from)
                        .collect(Collectors.toList()))
                .categories(recipe.getCategories().stream()
                        .map(Category::getCategory)
                        .collect(Collectors.toList()))
                .build();
    }
}
