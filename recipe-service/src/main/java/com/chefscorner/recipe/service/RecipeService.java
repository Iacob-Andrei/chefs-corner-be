package com.chefscorner.recipe.service;

import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.exception.RecipeNotFoundException;
import com.chefscorner.recipe.mapper.RecipeMapper;
import com.chefscorner.recipe.model.Recipe;
import com.chefscorner.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeDto getRecipeById(Integer idRecipe){
        Recipe recipe = recipeRepository.findById(idRecipe).orElseThrow(() -> new RecipeNotFoundException(idRecipe));
        return RecipeMapper.recipeToRecipeDto(recipe);
    }
}
