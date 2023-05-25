package com.chefscorner.recipe.controller;

import com.chefscorner.recipe.dto.CompleteMenuRequest;
import com.chefscorner.recipe.dto.PageDto;
import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{category},{offset}")
    public ResponseEntity<PageDto> recipesByCategoryOffset(@PathVariable("category") String category,
                                                     @PathVariable("offset") Integer offset){
        return ResponseEntity.ok().body(categoryService.getRecipesFromCategoriesOffset(category, offset));
    }

    @PostMapping("/complete-menu")
    public ResponseEntity<List<RecipeDto>> completeMenu(@RequestBody CompleteMenuRequest currentMenu){
        return ResponseEntity.ok().body(categoryService.addRecipesToMenu(currentMenu));
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<RecipeDto>> recipesByCategory(@PathVariable("category") String category){
        return ResponseEntity.ok().body(categoryService.getRecipesFromCategory(category));
    }
}
