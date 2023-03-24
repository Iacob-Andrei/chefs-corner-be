package com.chefscorner.recipe.controller;

import com.chefscorner.recipe.dto.PageDto;
import com.chefscorner.recipe.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{category},{offset}")
    public PageDto recipesByCategory(@PathVariable("category") String category,
                                     @PathVariable("offset") Integer offset){
        return categoryService.getRecipesFromCategories(category, offset);
    }
}