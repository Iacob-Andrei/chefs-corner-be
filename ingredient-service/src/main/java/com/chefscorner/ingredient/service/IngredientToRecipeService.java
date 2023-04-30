package com.chefscorner.ingredient.service;

import com.chefscorner.ingredient.dto.IngredientToRecipeDto;
import com.chefscorner.ingredient.exception.IngredientNotFoundException;
import com.chefscorner.ingredient.mapper.IngredientToRecipeMapper;
import com.chefscorner.ingredient.model.Ingredient;
import com.chefscorner.ingredient.model.IngredientToRecipe;
import com.chefscorner.ingredient.repository.IngredientRepository;
import com.chefscorner.ingredient.repository.IngredientToRecipeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class IngredientToRecipeService {

    private final IngredientToRecipeRepository ingredientToRecipeRepository;
    private final IngredientRepository ingredientRepository;

    public List<IngredientToRecipeDto> getIngredientsByIdRecipe(Integer id) {
        List<IngredientToRecipe> ingredientsList= ingredientToRecipeRepository.findIngredientsByIdRecipe(id);
        return ingredientsList.stream().map(IngredientToRecipeMapper::ingredientToRecipeToIngredientToRecipeDto).collect(Collectors.toList());
    }

    @Transactional
    public void addIngredientsNeeded(Integer idRecipe, List<IngredientToRecipeDto> ingredients) {
        for(IngredientToRecipeDto ingredientDto : ingredients){
            Optional<Ingredient> optional = ingredientRepository.findByName(ingredientDto.getName());
            if(optional.isEmpty()) throw new IngredientNotFoundException(ingredientDto.getName());

            IngredientToRecipe ingredientToRecipe = new IngredientToRecipe(
                    idRecipe,
                    optional.get(),
                    ingredientDto.getAmount(),
                    ingredientDto.getDescription(),
                    ingredientDto.getUnit(),
                    ingredientDto.getGrams()
            );
            ingredientToRecipeRepository.save(ingredientToRecipe);
        }
    }

    public Map<Integer,List<IngredientToRecipeDto>> getIngredientsByIdsRecipes(List<Integer> ids) {
        List<IngredientToRecipe> response = ingredientToRecipeRepository.findIngredientToRecipesByRecipeIsIn(ids);
        Map<Integer, List<IngredientToRecipeDto>> mapIngredients = new HashMap<>();

        for(Integer id : ids){
            mapIngredients.put(
                    id,
                    response.stream()
                            .filter(el -> Objects.equals(el.getRecipe(), id))
                            .map(IngredientToRecipeMapper::ingredientToRecipeToIngredientToRecipeDto).collect(Collectors.toList())
            );
        }
        return mapIngredients;
    }
}
