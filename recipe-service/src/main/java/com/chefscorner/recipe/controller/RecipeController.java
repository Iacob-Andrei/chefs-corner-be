package com.chefscorner.recipe.controller;

import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RecipeDto> recipeByidWithImage(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(recipeService.getRecipeById(id));
    }

    @GetMapping("/name/{pattern}")
    public ResponseEntity<List<RecipeDto>> findRecipesByNamePattern(@PathVariable("pattern") String pattern){
        return ResponseEntity.ok().body(recipeService.findRecipesByNamePattern(pattern));
    }
}
