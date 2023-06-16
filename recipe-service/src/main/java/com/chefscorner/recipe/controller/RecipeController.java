package com.chefscorner.recipe.controller;

import com.chefscorner.recipe.dto.PatchDataDto;
import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/recipe")
public class RecipeController {

    private final RecipeService recipeService;


    @Operation(summary = "Find recipe by id.")
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> recipeByIdWithImage(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") Integer id) throws JSONException {
        return ResponseEntity.ok().body(recipeService.getRecipeById(bearerToken, id));
    }

    @Operation(summary = "Find recipes by id.")
    @PostMapping("/list")
    public ResponseEntity<List<RecipeDto>> recipesByIds(@RequestBody List<Integer> ids){
        return ResponseEntity.ok().body(recipeService.getRecipesByIds(ids));
    }

    @Operation(summary = "Find recipe containing ingredients.")
    @PostMapping("/recommendation")
    public ResponseEntity<List<RecipeDto>> recipesWithIngredients(@RequestBody List<Integer> ids){
        return ResponseEntity.ok().body(recipeService.getRecipesByIngredients(ids));
    }

    @Operation(summary = "Find users recipes.")
    @GetMapping("/for-user")
    public ResponseEntity<List<RecipeDto>> getUsersRecipes(@RequestHeader("Authorization") String bearerToken) throws JSONException {
        return ResponseEntity.ok().body(recipeService.getUsersRecipes(bearerToken));
    }

    @Operation(summary = "Find recipes with name containing pattern.")
    @GetMapping("/name/{pattern}")
    public ResponseEntity<List<RecipeDto>> findRecipesByNamePattern(@PathVariable("pattern") String pattern){
        return ResponseEntity.ok().body(recipeService.findRecipesByNamePattern(pattern));
    }

    @Operation(summary = "Post a new recipe.")
    @PostMapping("")
    public ResponseEntity<RecipeDto> postNewRecipe(@RequestBody RecipeDto recipe){
        return ResponseEntity.ok().body(recipeService.saveRecipe(recipe));
    }

    @Operation(summary = "Patch a recipe.")
    @PatchMapping("")
    public ResponseEntity<?> patchRecipe(@RequestHeader("Authorization") String bearerToken, @RequestBody RecipeDto recipe) throws JSONException {
        recipeService.patchRecipe(bearerToken, recipe);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Patch the image of a recipe.")
    @PatchMapping("/image")
    public ResponseEntity<String> updateRecipeImage(@RequestBody PatchDataDto body) {
        recipeService.updateRecipeImage(body);
        return ResponseEntity.ok().body("ok");
    }

    @Operation(summary = "Delete a recipe.")
    @DeleteMapping("/{id}")
    public void deleteRecipe(@RequestHeader("Authorization") String bearerToken,@PathVariable("id")Integer id) throws JSONException {
        recipeService.deleteRecipe(bearerToken, id);
    }

    @Operation(summary = "Get information about owner recipe.")
    @GetMapping("/owner/{id}")
    public ResponseEntity<List<String>> getRecipeOwnerEmail(@PathVariable Integer id){
        return ResponseEntity.ok().body(recipeService.getRecipeOwnerEmail(id));
    }
}