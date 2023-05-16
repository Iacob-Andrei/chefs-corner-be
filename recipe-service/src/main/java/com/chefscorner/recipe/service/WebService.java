package com.chefscorner.recipe.service;

import com.chefscorner.recipe.dto.IngredientInRecipeDto;
import com.chefscorner.recipe.exception.IngredientNotFoundException;
import com.chefscorner.recipe.model.IngredientInRecipe;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WebService {

    private final WebClient.Builder webClientBuilder;
    private static final ParameterizedTypeReference<Map<Integer, List<IngredientInRecipe>>> MAP_TYPE_REF = new ParameterizedTypeReference<>() {};

    public List<IngredientInRecipe> getIngredientsForRecipe(Integer idRecipe){
        return List.of(Objects.requireNonNull(webClientBuilder.build()
                .get()
                .uri("http://ingredient-service/api/ingredient/from-recipe/" + idRecipe)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(IngredientInRecipe[].class)
                .block()));
    }

    public Map<Integer, List<IngredientInRecipe>> getIngredientsForRecipes(List<Integer> ids){
        return webClientBuilder.build()
                .post()
                .uri("http://ingredient-service/api/ingredient/from-recipe/list")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(ids)
                .retrieve()
                .bodyToMono(MAP_TYPE_REF)
                .block();
    }

    public Map<Integer, List<IngredientInRecipe>> getRecipesByIngredients(List<Integer> ids){
        return webClientBuilder.build()
                .post()
                .uri("http://ingredient-service/api/ingredient/recommendation")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(ids)
                .retrieve()
                .bodyToMono(MAP_TYPE_REF)
                .block();
    }

    public void postIngredientsForRecipe(Integer idRecipe, List<IngredientInRecipeDto> ingredients){
        webClientBuilder.build()
                .post()
                .uri("http://ingredient-service/api/ingredient/add/" + idRecipe)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(ingredients)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class)
                        .doOnSuccess(body -> {
                            if (!body.equals("ok")) {
                                throw new IngredientNotFoundException(body);
                            }
                        }))
                .block();
    }

    public List<Integer> getUsersPermissions(String email){
        return List.of(Objects.requireNonNull(webClientBuilder.build()
                .get()
                .uri("http://user-service/api/permission/for-user/" + email)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Integer[].class)
                .block()));
    }
}
