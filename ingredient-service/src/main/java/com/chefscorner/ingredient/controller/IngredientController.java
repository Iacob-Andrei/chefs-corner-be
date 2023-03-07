package com.chefscorner.ingredient.controller;

import com.chefscorner.ingredient.dto.IngredientToRecipeDto;
import com.chefscorner.ingredient.service.IngredientToRecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/ingredient")
public class IngredientController {

    private final IngredientToRecipeService ingredientToRecipeService;

    @GetMapping("from-recipe/{id}")
    public List<IngredientToRecipeDto> getIngredientsByIdRecipe(@PathVariable("id") Integer id){
        return ingredientToRecipeService.getIngredientsByIdRecipe(id);
    }
}
