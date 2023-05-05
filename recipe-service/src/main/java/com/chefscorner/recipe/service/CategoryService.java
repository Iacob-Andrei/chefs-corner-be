package com.chefscorner.recipe.service;

import com.chefscorner.recipe.dto.CompleteMenuRequest;
import com.chefscorner.recipe.dto.PageDto;
import com.chefscorner.recipe.dto.RecipeDto;
import com.chefscorner.recipe.exception.InvalidNumberPage;
import com.chefscorner.recipe.mapper.RecipeMapper;
import com.chefscorner.recipe.model.Category;
import com.chefscorner.recipe.model.IngredientInRecipe;
import com.chefscorner.recipe.model.Recipe;
import com.chefscorner.recipe.repository.CategoryRepository;
import com.chefscorner.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final WebService webService;

    public PageDto getRecipesFromCategories(String category, Integer page) {
        if(page < 0){ throw new InvalidNumberPage(page, category); }

        Page<Recipe> slice = category.equals("") ? categoryRepository.findByPage(PageRequest.of(page, 20)) :
                                                   categoryRepository.findByCategory(category, PageRequest.of(page, 20));
        List<Recipe> recipesInBatch = slice.getContent();

        if(slice.getTotalPages() == 0){ throw new InvalidNumberPage(category); }
        if(recipesInBatch.size() == 0){ throw new InvalidNumberPage(page, slice.getTotalPages()-1,category); }

        return PageDto.builder()
                .currentPage(page)
                .totalPages(slice.getTotalPages()-1)
                .recipes(recipesInBatch.stream().map(RecipeMapper::recipeToRecipeDtoOnlyInfo).collect(Collectors.toList()))
                .build();
    }

    public void saveCategories(List<String> categories, Recipe recipe) {
        for(String categoryName : categories){
            Category category = new Category(recipe, categoryName.replace(" ", ""));
            categoryRepository.save(category);
        }
    }

    public List<RecipeDto> addRecipesToMenu(CompleteMenuRequest currentMenu) {
        List<Recipe> allRecipesInMenu = currentMenuToListRecipe(currentMenu.getCurrentMenu());
        List<Integer> ids = new ArrayList<>(allRecipesInMenu.stream().map(Recipe::getId).toList());

        for(String category : currentMenu.getRequested().keySet()){
            List<Recipe> recipesInCategory = categoryRepository.findCategory(category);
            recipesInCategory = recipesInCategory.stream().filter(recipe -> !allRecipesInMenu.contains(recipe)).collect(Collectors.toList());

            int added = 0;
            while(added != currentMenu.getRequested().get(category)){
                Collections.shuffle(recipesInCategory);
                Recipe recipe = recipesInCategory.remove(0);

                Category cat = recipe.getCategories().stream().filter(category1 -> category1.getCategory().equals(category)).findFirst().orElse(null);
                recipe.getCategories().remove(cat);
                recipe.getCategories().add(0, cat);

                allRecipesInMenu.add(recipe);
                added++;
                ids.add(recipe.getId());
            }
        }

        Map<Integer, List<IngredientInRecipe>> ingredients = webService.getIngredientsForRecipes(ids);
        return allRecipesInMenu.stream().map(recipe -> RecipeMapper.recipeToRecipeDtoWithoutDirections(recipe, ingredients.get(recipe.getId()))).collect(Collectors.toList());
    }

    private List<Recipe> currentMenuToListRecipe(Map<String,List<Integer>> currentMenu){
        List<Recipe> allRecipesInMenu = new ArrayList<>();
        for(String category : currentMenu.keySet()){
            for(Integer idRecipe : currentMenu.get(category)){
                Recipe recipe = recipeRepository.getReferenceById(idRecipe);
                Category cat = recipe.getCategories().stream().filter(category1 -> category1.getCategory().equals(category)).findFirst().orElse(null);
                recipe.getCategories().remove(cat);
                recipe.getCategories().add(0, cat);
                allRecipesInMenu.add(recipe);
            }
        }
        return allRecipesInMenu;
    }
}
