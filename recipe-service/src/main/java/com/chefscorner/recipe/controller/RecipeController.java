package com.chefscorner.recipe.controller;

import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping("/{id}")
    public RecipeDto recipeByidWithImage(@PathVariable("id") Integer id){
        return recipeService.getRecipeById(id);
    }

    @GetMapping("/name/{pattern}")
    public List<RecipeDto> findRecipesByNamePattern(@PathVariable("pattern") String pattern){
        return recipeService.findRecipesByNamePattern(pattern);
    }
}
