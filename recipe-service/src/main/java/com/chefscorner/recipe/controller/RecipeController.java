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


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> recipeByIdWithImage(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(recipeService.getRecipeById(id));
    }

    @GetMapping("/for-user")
    public ResponseEntity<?> getUsersRecipes(@RequestHeader("Authorization") String bearerToken) throws JSONException {
        return ResponseEntity.ok().body(recipeService.getUsersRecipes(bearerToken));
    }

    @GetMapping("/name/{pattern}")
    public ResponseEntity<List<RecipeDto>> findRecipesByNamePattern(@PathVariable("pattern") String pattern){
        return ResponseEntity.ok().body(recipeService.findRecipesByNamePattern(pattern));
    }

    @PostMapping("")
    public ResponseEntity<RecipeDto> postNewRecipe(@RequestBody RecipeDto recipe){
        System.out.println(recipe);
        return ResponseEntity.ok().body(recipeService.saveRecipe(recipe));
    }

    @PatchMapping("/image/{id}")
    public void updateRecipeImage(@PathVariable("id")Integer id, @RequestParam MultipartFile image) throws IOException {
        recipeService.updateRecipeImage(id, image);
    }
}