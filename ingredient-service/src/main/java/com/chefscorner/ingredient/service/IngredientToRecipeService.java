package com.chefscorner.ingredient.service;

import com.chefscorner.ingredient.dto.IngredientToRecipeDto;
import com.chefscorner.ingredient.mapper.IngredientToRecipeMapper;
import com.chefscorner.ingredient.model.IngredientToRecipe;
import com.chefscorner.ingredient.repository.IngredientToRecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientToRecipeService {

    private final IngredientToRecipeRepository ingredientToRecipeRepository;

    public List<IngredientToRecipeDto> getIngredientsByIdRecipe(Integer id) {
        List<IngredientToRecipe> ingredientsList= ingredientToRecipeRepository.findIngredientsByIdRecipe(id);
        return ingredientsList.stream().map(IngredientToRecipeMapper::ingredientToRecipeToIngredientToRecipeDto).collect(Collectors.toList());
    }
}
