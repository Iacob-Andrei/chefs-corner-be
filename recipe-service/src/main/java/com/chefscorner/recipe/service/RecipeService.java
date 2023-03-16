package com.chefscorner.recipe.service;

import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.exception.RecipeNotFoundException;
import com.chefscorner.recipe.mapper.RecipeMapper;
import com.chefscorner.recipe.model.IngredientToRecipe;
import com.chefscorner.recipe.model.Recipe;
import com.chefscorner.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final WebClient.Builder webClientBuilder;

    public RecipeDto getRecipeById(Integer idRecipe){
        Recipe recipe = recipeRepository.findById(idRecipe).orElseThrow(() -> new RecipeNotFoundException(idRecipe));

        List<IngredientToRecipe> response = List.of(Objects.requireNonNull(webClientBuilder.build().get()
                .uri("http://ingredient-service/api/ingredient/from-recipe/" + idRecipe)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(IngredientToRecipe[].class)
                .block()));

        return RecipeMapper.recipeToRecipeDto(recipe, response);
    }
}
