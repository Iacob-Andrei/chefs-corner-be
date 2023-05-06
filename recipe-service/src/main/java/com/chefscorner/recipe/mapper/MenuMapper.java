package com.chefscorner.recipe.mapper;

import com.chefscorner.recipe.dto.MenuDto;
import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.model.IngredientInRecipe;
import com.chefscorner.recipe.model.Menu;
import com.chefscorner.recipe.model.Recipe;
import com.chefscorner.recipe.model.RecipeInMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuMapper {

    public static MenuDto menuToMenuDtoWithoutIngredients(Menu menu){
        Map<String, List<RecipeDto>> recipes = new HashMap<>();
        for(RecipeInMenu recipe : menu.getRecipes()){
            if(recipes.containsKey(recipe.getCategory())){
                recipes.get(recipe.getCategory()).add(RecipeMapper.recipeToRecipeDtoOnlyInfo(recipe.getRecipe()));
            }else{
                List<RecipeDto> recipeDtos = new ArrayList<>();
                recipeDtos.add(RecipeMapper.recipeToRecipeDtoOnlyInfo(recipe.getRecipe()));
                recipes.put(recipe.getCategory(), recipeDtos);
            }
        }

        return MenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .recipes(recipes)
                .build();
    }

    public static MenuDto menuToMenuDtoWithIngredients(Menu menu, Map<Integer, List<IngredientInRecipe>> ingredients){
        Map<String, List<RecipeDto>> recipes = new HashMap<>();
        for(RecipeInMenu recipeInMenu : menu.getRecipes()){
            Recipe recipe = recipeInMenu.getRecipe();
            List<IngredientInRecipe> ingredientInRecipes = ingredients.get(recipe.getId());

            if(recipes.containsKey(recipeInMenu.getCategory())){
                recipes.get(recipeInMenu.getCategory()).add(RecipeMapper.recipeToRecipeDtoWithoutDirections(recipe, ingredientInRecipes));
            }else{
                List<RecipeDto> recipeDtos = new ArrayList<>();
                recipeDtos.add(RecipeMapper.recipeToRecipeDtoWithoutDirections(recipe, ingredientInRecipes));
                recipes.put(recipeInMenu.getCategory(), recipeDtos);
            }
        }

        return MenuDto.builder()
                .id(menu.getId())
                .name(menu.getName())
                .recipes(recipes)
                .build();
    }
}
