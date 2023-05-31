package com.chefscorner.recipe.service;

import com.chefscorner.recipe.dto.CompleteMenuRequest;
import com.chefscorner.recipe.dto.PageDto;
import com.chefscorner.recipe.exception.InvalidNumberPage;
import com.chefscorner.recipe.mapper.RecipeMapper;
import com.chefscorner.recipe.model.Category;
import com.chefscorner.recipe.model.Menu;
import com.chefscorner.recipe.model.Recipe;
import com.chefscorner.recipe.model.RecipeInMenu;
import com.chefscorner.recipe.repository.CategoryRepository;
import com.chefscorner.recipe.repository.RecipeRepository;
import com.chefscorner.recipe.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final MenuService menuService;
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

    @Transactional
    public void addRecipesToMenu(String bearerToken, CompleteMenuRequest currentMenu) throws JSONException {
        String owner = JwtUtil.getSubjectFromToken(bearerToken);
        Menu menu = menuService.getMenu(currentMenu.getIdMenu(), owner);

        List<Recipe> allRecipesInMenu = menu.getRecipes().stream().map(RecipeInMenu::getRecipe).toList();

        for(String category : currentMenu.getRequested().keySet()){
            List<Recipe> recipesInCategory = categoryRepository.findCategory(category);
            recipesInCategory = recipesInCategory.stream().filter(recipe -> !allRecipesInMenu.contains(recipe)).collect(Collectors.toList());

            int added = 0;
            while(added != currentMenu.getRequested().get(category)){
                Collections.shuffle(recipesInCategory);
                Recipe recipe = recipesInCategory.remove(0);

                menuService.addToMenu(menu, recipe, category);
                added++;
            }
        }
    }
}
