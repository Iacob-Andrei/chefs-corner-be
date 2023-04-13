package com.chefscorner.recipe.service;

import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.exception.RecipeNotFoundException;
import com.chefscorner.recipe.mapper.RecipeMapper;
import com.chefscorner.recipe.model.IngredientInRecipe;
import com.chefscorner.recipe.model.Recipe;
import com.chefscorner.recipe.repository.RecipeRepository;
import com.chefscorner.recipe.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RecipeService {

    private final DirectionService directionService;
    private final RecipeRepository recipeRepository;
    private final WebClient.Builder webClientBuilder;

    public RecipeDto getRecipeById(Integer idRecipe){
        Recipe recipe = recipeRepository.findById(idRecipe).orElseThrow(() -> new RecipeNotFoundException(idRecipe));

        List<IngredientInRecipe> response = List.of(Objects.requireNonNull(webClientBuilder.build().get()
                .uri("http://ingredient-service/api/ingredient/from-recipe/" + idRecipe)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(IngredientInRecipe[].class)
                .block()));

        return RecipeMapper.recipeToRecipeDto(recipe, response);
    }

    public List<RecipeDto> findRecipesByNamePattern(String pattern) {
        List<Recipe> result = recipeRepository.findByNameContainingIgnoreCase(pattern, PageRequest.of(0, 20));
        return result.stream().map(RecipeMapper::recipeToRecipeDtoOnlyInfo).collect(Collectors.toList());
    }

    public RecipeDto saveRecipe(RecipeDto requestData) {
        Recipe recipe = new Recipe(
                requestData.getName(),
                requestData.getPrep_time(),
                requestData.getCook_time(),
                requestData.getNumber_servings(),
                requestData.getOwner()
        );

        recipeRepository.save(recipe);
        directionService.saveDirections(requestData.getDirections(), recipe);

        return RecipeDto.builder().id(recipe.getId()).build();
    }

    public void updateRecipeImage(Integer id, MultipartFile image) throws IOException {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if(recipeOptional.isEmpty()) return;    //TODO: return exception

        Recipe recipe = recipeOptional.get();
        recipe.setImage_data(ImageUtil.compressImage(image.getBytes()));
        recipeRepository.save(recipe);
    }
}
