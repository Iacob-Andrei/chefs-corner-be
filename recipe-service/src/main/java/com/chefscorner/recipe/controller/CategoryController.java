package com.chefscorner.recipe.controller;

import com.chefscorner.recipe.dto.CompleteMenuRequest;
import com.chefscorner.recipe.dto.PageDto;
import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get recipes by category and page number.")
    @GetMapping("/{category},{offset}")
    public ResponseEntity<PageDto> recipesByCategory(@PathVariable("category") String category,
                                                     @PathVariable("offset") Integer offset){

        return ResponseEntity.ok().body(categoryService.getRecipesFromCategories(category, offset));
    }

    @Operation(summary = "Add recommended recipes to menu.")
    @PostMapping("/complete-menu")
    public ResponseEntity<List<RecipeDto>> completeMenu(@RequestHeader("Authorization") String bearerToken,
                                                        @RequestBody CompleteMenuRequest currentMenu) throws JSONException {
        categoryService.addRecipesToMenu(bearerToken, currentMenu);
        return ResponseEntity.ok().build();
    }
}
