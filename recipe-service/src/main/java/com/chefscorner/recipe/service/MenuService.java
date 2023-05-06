package com.chefscorner.recipe.service;

import com.chefscorner.recipe.dto.CompleteMenuRequest;
import com.chefscorner.recipe.exception.RecipeNotFoundException;
import com.chefscorner.recipe.model.Menu;
import com.chefscorner.recipe.model.Recipe;
import com.chefscorner.recipe.model.RecipeInMenu;
import com.chefscorner.recipe.repository.MenuRepository;
import com.chefscorner.recipe.repository.RecipeInMenuRepository;
import com.chefscorner.recipe.repository.RecipeRepository;
import com.chefscorner.recipe.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RecipeInMenuRepository recipeInMenuRepository;
    private final RecipeRepository recipeRepository;

    @Transactional
    public void saveMenu(String bearerToken, CompleteMenuRequest menuDto) throws JSONException {
        String owner = JwtUtil.getSubjectFromToken(bearerToken);
        Menu menu = new Menu(menuDto.getName(), owner);

        menuRepository.save(menu);
        for(String category : menuDto.getCurrentMenu().keySet()){
            for(Integer idRecipe : menuDto.getCurrentMenu().get(category)){
                Optional<Recipe> recipeOptional = recipeRepository.findById(idRecipe);
                if(recipeOptional.isEmpty()) throw new RecipeNotFoundException(idRecipe);

                RecipeInMenu recipeUsed = new RecipeInMenu(recipeOptional.get(), menu, category);
                recipeInMenuRepository.save(recipeUsed);
            }
        }
    }
}
