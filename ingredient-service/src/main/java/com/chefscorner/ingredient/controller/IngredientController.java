package com.chefscorner.ingredient.controller;

import com.chefscorner.ingredient.dto.IngredientDto;
import com.chefscorner.ingredient.dto.IngredientToRecipeDto;
import com.chefscorner.ingredient.service.IngredientService;
import com.chefscorner.ingredient.service.IngredientToRecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/ingredient")
public class IngredientController {

    private final IngredientToRecipeService ingredientToRecipeService;
    private final IngredientService ingredientService;

    @GetMapping("from-recipe/{id}")
    public ResponseEntity<List<IngredientToRecipeDto>> getIngredientsByIdRecipe(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(ingredientToRecipeService.getIngredientsByIdRecipe(id));
    }

    @GetMapping("/{pattern}")
    public ResponseEntity<List<IngredientDto>> ingredientsByNamePattern(@PathVariable("pattern") String pattern){
        return ResponseEntity.ok().body(ingredientService.findIngredientsByNamePattern(pattern));
    }
}
