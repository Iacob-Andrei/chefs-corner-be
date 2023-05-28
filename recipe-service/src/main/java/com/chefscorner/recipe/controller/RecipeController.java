package com.chefscorner.recipe.controller;

import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> recipeByIdWithImage(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") Integer id) throws JSONException {
        return ResponseEntity.ok().body(recipeService.getRecipeById(bearerToken, id));
    }

    @PostMapping("/list")
    public ResponseEntity<List<RecipeDto>> recipesByIds(@RequestBody List<Integer> ids){
        return ResponseEntity.ok().body(recipeService.getRecipesByIds(ids));
    }

    @PostMapping("/recommendation")
    public ResponseEntity<List<RecipeDto>> recipesWithIngredients(@RequestBody List<Integer> ids){
        return ResponseEntity.ok().body(recipeService.getRecipesByIngredients(ids));
    }

    @GetMapping("/for-user")
    public ResponseEntity<List<RecipeDto>> getUsersRecipes(@RequestHeader("Authorization") String bearerToken) throws JSONException {
        return ResponseEntity.ok().body(recipeService.getUsersRecipes(bearerToken));
    }

    @GetMapping("/name/{pattern}")
    public ResponseEntity<List<RecipeDto>> findRecipesByNamePattern(@PathVariable("pattern") String pattern){
        return ResponseEntity.ok().body(recipeService.findRecipesByNamePattern(pattern));
    }

    @PostMapping("")
    public ResponseEntity<RecipeDto> postNewRecipe(@RequestBody RecipeDto recipe){
        return ResponseEntity.ok().body(recipeService.saveRecipe(recipe));
    }

    @PatchMapping("/image/{id}")
    public void updateRecipeImage(@PathVariable("id")Integer id, @RequestParam MultipartFile image) throws IOException {
        recipeService.updateRecipeImage(id, image);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@RequestHeader("Authorization") String bearerToken,@PathVariable("id")Integer id) throws JSONException {
        recipeService.deleteRecipe(bearerToken, id);
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<List<String>> getRecipeOwnerEmail(@PathVariable Integer id){
        System.out.println(id);
        return ResponseEntity.ok().body(recipeService.getRecipeOwnerEmail(id));
    }
}