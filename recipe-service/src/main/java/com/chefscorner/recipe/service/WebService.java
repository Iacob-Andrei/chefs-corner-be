package com.chefscorner.recipe.service;

import com.chefscorner.recipe.model.IngredientInRecipe;
import lombok.RequiredArgsConstructor;
import org.jvnet.hk2.annotations.Service;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WebService {

    private final WebClient.Builder webClientBuilder;

    public List<IngredientInRecipe> getIngredientsForRecipe(Integer idRecipe){
        return List.of(Objects.requireNonNull(webClientBuilder.build().get()
                .uri("http://ingredient-service/api/ingredient/from-recipe/" + idRecipe)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(IngredientInRecipe[].class)
                .block()));
    }

    public void postIngredientsForRecipe(Integer idRecipe){

    }
}
