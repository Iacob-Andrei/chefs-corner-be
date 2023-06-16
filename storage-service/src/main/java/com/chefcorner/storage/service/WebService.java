package com.chefcorner.storage.service;

import com.chefcorner.storage.dto.PatchImageBodyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class WebService {

    private final WebClient.Builder webClientBuilder;

    public String patchProfileImage(PatchImageBodyDto body){
        return webClientBuilder.build()
                .patch()
                .uri("http://user-service/api/user/image")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String patchRecipeImage(PatchImageBodyDto body) {
        return webClientBuilder.build()
                .patch()
                .uri("http://recipe-service/api/recipe/image")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String patchVideoDirection(PatchImageBodyDto body) {
        return webClientBuilder.build()
                .patch()
                .uri("http://recipe-service/api/direction/video")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
