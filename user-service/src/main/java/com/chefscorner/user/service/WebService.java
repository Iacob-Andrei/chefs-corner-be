package com.chefscorner.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class WebService {

    private final WebClient.Builder webClientBuilder;
    private static final ParameterizedTypeReference<List<String>> LIST_STR_TYPE_REF = new ParameterizedTypeReference<>() {};

    public List<String> getRecipeOwnerEmail(Integer idRecipe){
        return webClientBuilder.build()
                .get()
                .uri("http://recipe-service/api/recipe/owner/"+idRecipe)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(LIST_STR_TYPE_REF)
                .block();
    }
}
