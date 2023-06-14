package com.chefscorner.recipe.service;

import com.chefscorner.recipe.dto.PatchDataDto;
import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.exception.RecipeForbiddenException;
import com.chefscorner.recipe.exception.RecipeNotFoundException;
import com.chefscorner.recipe.mapper.RecipeMapper;
import com.chefscorner.recipe.model.IngredientInRecipe;
import com.chefscorner.recipe.model.Recipe;
import com.chefscorner.recipe.repository.RecipeRepository;
import com.chefscorner.recipe.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

        String email = JwtUtil.getSubjectFromToken(bearerToken);
        if(!recipe.getOwner().equals("public")){
            if(!recipe.getOwner().equals(email) && !webService.getUsersPermissions(email).contains(idRecipe)) {
                throw new RecipeForbiddenException();
            }
        }

        List<IngredientInRecipe> response = webService.getIngredientsForRecipe(idRecipe);
        for(IngredientInRecipe ingredient : response){
            ingredient.setPrices(
                    ingredient.getPrices().stream()
                            .filter(ingredientPrice -> ingredientPrice.getOwner().equals(email))
                            .collect(Collectors.toList())
            );
        }

        return RecipeMapper.recipeToRecipeDto(recipe, response, webService);
    }

    public List<RecipeDto> findRecipesByNamePattern(String pattern) {
        List<Recipe> result = recipeRepository.findByNameContainingIgnoreCase(pattern, PageRequest.of(0, 20));
        return result.stream().map(recipe ->
                RecipeMapper.recipeToRecipeDtoOnlyInfo(
                        recipe,
                        recipe.getOwner().equals("public") ? new byte[0] : webService.getFile(recipe.getImage())
                ))
                .collect(Collectors.toList());
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

    public void updateRecipeImage(PatchDataDto body) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(body.getIdRecipe());
        if(recipeOptional.isEmpty()) throw new RecipeNotFoundException(body.getIdRecipe());

        Recipe recipe = recipeOptional.get();
        recipe.setImage(body.getImageId());
        recipeRepository.save(recipe);
    }

    public List<RecipeDto> getUsersRecipes(String bearerToken) throws JSONException {
        String email = JwtUtil.getSubjectFromToken(bearerToken);
        List<Recipe> result = recipeRepository.findRecipeByOwner(email);

        for(int idRecipe : webService.getUsersPermissions(email)) {
            Optional<Recipe> optional = recipeRepository.findById(idRecipe);
            optional.ifPresent(result::add);
        }

        return result.stream().map(recipe -> RecipeMapper.recipeToRecipeDtoOnlyInfo(
                recipe,
                recipe.getOwner().equals("public") ? new byte[0] : webService.getFile(recipe.getImage())
        )).collect(Collectors.toList());
    }

    @Transactional
    public void patchRecipe(String bearerToken, RecipeDto request) throws JSONException {
        Optional<Recipe> optional = recipeRepository.findById(request.getId());
        if(optional.isEmpty()) throw new RecipeNotFoundException(request.getId());

        Recipe recipe = optional.get();
        if(!recipe.getOwner().equals(JwtUtil.getSubjectFromToken(bearerToken))){
            throw new RecipeNotFoundException(request.getId());
        }

        recipe.setName(request.getName());
        recipe.setPrep_time(request.getPrep_time());
        recipe.setCook_time(request.getCook_time());
        recipe.setNumber_servings(request.getNumber_servings());
        recipeRepository.save(recipe);

        directionService.patchDirectionRecipes(recipe, request.getDirections());
        categoryService.patchCategories(request.getCategories(), recipe);
        webService.patchIngredientsForRecipe(recipe.getId(), request.getIngredients());
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
            recipeDtoList.add(RecipeMapper.recipeToRecipeDtoWithoutDirections(
                    recipe,
                    mapIngredients.get(recipe.getId()),
                    recipe.getOwner().equals("public") ? new byte[0] : webService.getFile(recipe.getImage())
                    )
            );
        }

        return recipeDtoList;
    }

    public List<RecipeDto> getRecipesByIngredients(List<Integer> ids) {

        Map<Integer, List<IngredientInRecipe>> mapIngredients = webService.getRecipesByIngredients(ids);
        List<Integer> recipeIds = mapIngredients.keySet().stream().toList();
        List<Recipe> response = recipeRepository.findRecipesByIdIn(recipeIds);

        List<RecipeDto> recipeDtoList = new ArrayList<>();
        for(Recipe recipe : response){
            recipeDtoList.add(RecipeMapper.recipeToRecipeDtoWithoutDirections(
                    recipe,
                    mapIngredients.get(recipe.getId()),
                    recipe.getOwner().equals("public") ? new byte[0] : webService.getFile(recipe.getImage())
                    )
            );
        }

        return recipeDtoList;
    }

    public List<String> getRecipeOwnerEmail(Integer id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if(recipeOptional.isEmpty()) throw new RecipeNotFoundException(id);

        return Arrays.asList(recipeOptional.get().getOwner(), recipeOptional.get().getName());
    }
}
