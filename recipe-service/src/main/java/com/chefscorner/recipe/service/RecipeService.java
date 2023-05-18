package com.chefscorner.recipe.service;

import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.exception.RecipeNotFoundException;
import com.chefscorner.recipe.mapper.RecipeMapper;
import com.chefscorner.recipe.model.IngredientInRecipe;
import com.chefscorner.recipe.model.Recipe;
import com.chefscorner.recipe.repository.RecipeRepository;
import com.chefscorner.recipe.util.ImageUtil;
import com.chefscorner.recipe.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RecipeService {

    private final DirectionService directionService;
    private final RecipeRepository recipeRepository;
    private final CategoryService categoryService;
    private final WebService webService;

    public RecipeDto getRecipeById(String bearerToken, Integer idRecipe) throws JSONException {
        Recipe recipe = recipeRepository.findById(idRecipe).orElseThrow(() -> new RecipeNotFoundException(idRecipe));

        if(!recipe.getOwner().equals("public")){
            String email = JwtUtil.getSubjectFromToken(bearerToken);
            if(!recipe.getOwner().equals(email) && !webService.getUsersPermissions(email).contains(idRecipe)) {
                throw new RecipeNotFoundException(idRecipe);
            }
        }

        List<IngredientInRecipe> response = webService.getIngredientsForRecipe(idRecipe);
        return RecipeMapper.recipeToRecipeDto(recipe, response);
    }

    public List<RecipeDto> findRecipesByNamePattern(String pattern) {
        List<Recipe> result = recipeRepository.findByNameContainingIgnoreCase(pattern, PageRequest.of(0, 20));
        return result.stream().map(RecipeMapper::recipeToRecipeDtoOnlyInfo).collect(Collectors.toList());
    }

    @Transactional
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
        categoryService.saveCategories(requestData.getCategories(), recipe);
        webService.postIngredientsForRecipe(recipe.getId(), requestData.getIngredients());

        return RecipeDto.builder().id(recipe.getId()).build();
    }

    public void updateRecipeImage(Integer id, MultipartFile image) throws IOException {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if(recipeOptional.isEmpty()) throw new RecipeNotFoundException(id);

        Recipe recipe = recipeOptional.get();
        recipe.setImage_data(ImageUtil.compressImage(image.getBytes()));
        recipeRepository.save(recipe);
    }

    public List<RecipeDto> getUsersRecipes(String bearerToken) throws JSONException {
        String email = JwtUtil.getSubjectFromToken(bearerToken);
        List<Recipe> result = recipeRepository.findRecipeByOwner(email);

        for(int idRecipe : webService.getUsersPermissions(email)) {
            Optional<Recipe> optional = recipeRepository.findById(idRecipe);
            optional.ifPresent(result::add);
        }

        return result.stream().map(RecipeMapper::recipeToRecipeDtoOnlyInfo).collect(Collectors.toList());
    }

    public void deleteRecipe(String bearerToken, Integer id) throws JSONException {
        Optional<Recipe> optional = recipeRepository.findById(id);
        if(optional.isEmpty()) throw new RecipeNotFoundException(id);

        Recipe recipe = optional.get();
        if(!recipe.getOwner().equals(JwtUtil.getSubjectFromToken(bearerToken))){
            throw new RecipeNotFoundException(id);
        }
        recipeRepository.delete(recipe);
    }

    public List<RecipeDto> getRecipesByIds(List<Integer> recipeIdList) {

        List<Recipe> response = recipeRepository.findRecipesByIdIn(recipeIdList);
        Map<Integer, List<IngredientInRecipe>> mapIngredients = webService.getIngredientsForRecipes(recipeIdList);

        List<RecipeDto> recipeDtoList = new ArrayList<>();
        for(Recipe recipe : response){
            recipeDtoList.add(RecipeMapper.recipeToRecipeDtoWithoutDirections(recipe, mapIngredients.get(recipe.getId())));
        }

        return recipeDtoList;
    }

    public List<RecipeDto> getRecipesByIngredients(List<Integer> ids) {

        Map<Integer, List<IngredientInRecipe>> mapIngredients = webService.getRecipesByIngredients(ids);
        List<Integer> recipeIds = mapIngredients.keySet().stream().toList();
        List<Recipe> response = recipeRepository.findRecipesByIdIn(recipeIds);

        List<RecipeDto> recipeDtoList = new ArrayList<>();
        for(Recipe recipe : response){
            recipeDtoList.add(RecipeMapper.recipeToRecipeDtoWithoutDirections(recipe, mapIngredients.get(recipe.getId())));
        }

        return recipeDtoList;
    }
}
