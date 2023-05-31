package com.chefscorner.recipe.service;

import com.chefscorner.recipe.dto.CompleteMenuRequest;
import com.chefscorner.recipe.dto.MenuDto;
import com.chefscorner.recipe.dto.RecipeInMenuDto;
import com.chefscorner.recipe.exception.MenuNotFoundException;
import com.chefscorner.recipe.exception.RecipeNotFoundException;
import com.chefscorner.recipe.mapper.MenuMapper;
import com.chefscorner.recipe.model.IngredientInRecipe;
import com.chefscorner.recipe.model.Menu;
import com.chefscorner.recipe.model.Recipe;
import com.chefscorner.recipe.model.RecipeInMenu;
import com.chefscorner.recipe.repository.MenuRepository;
import com.chefscorner.recipe.repository.RecipeInMenuRepository;
import com.chefscorner.recipe.repository.RecipeRepository;
import com.chefscorner.recipe.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RecipeInMenuRepository recipeInMenuRepository;
    private final RecipeRepository recipeRepository;
    private final WebService webService;


    public void saveMenu(String bearerToken, CompleteMenuRequest menuDto) throws JSONException {
        String owner = JwtUtil.getSubjectFromToken(bearerToken);
        Menu menu = new Menu(menuDto.getName(), owner, menuDto.getDescription());

        menuRepository.save(menu);
    }

    public List<MenuDto> getUsersMenus(String bearerToken) throws JSONException {
        String owner = JwtUtil.getSubjectFromToken(bearerToken);
        List<Menu> menus = menuRepository.findMenusByOwner(owner);

        return menus.stream().map(MenuMapper::menuToMenuDtoWithoutIngredients).collect(Collectors.toList());
    }

    public MenuDto getMenu(String bearerToken, Integer idMenu) throws JSONException {
        String owner = JwtUtil.getSubjectFromToken(bearerToken);
        Optional<Menu> menuOptional = menuRepository.findById(idMenu);

        if(menuOptional.isEmpty()) throw new MenuNotFoundException(idMenu);

        Menu menu = menuOptional.get();
        if(!menu.getOwner().equals(owner)) throw new MenuNotFoundException(idMenu);

        List<Integer> recipesId = menu.getRecipes().stream().map(recipeInMenu -> recipeInMenu.getRecipe().getId()).toList();
        Map<Integer, List<IngredientInRecipe>> ingredients = webService.getIngredientsForRecipes(recipesId);

        return MenuMapper.menuToMenuDtoWithIngredients(menu, ingredients);
    }

    public void addRecipeToMenu(String bearerToken, RecipeInMenuDto body) throws JSONException {
        String owner = JwtUtil.getSubjectFromToken(bearerToken);

        Optional<Recipe> optional = recipeRepository.findById(body.getIdRecipe());

        if(optional.isEmpty()) throw new RecipeNotFoundException(body.getIdRecipe());
        Recipe recipe = optional.get();
        if(!recipe.getOwner().equals(owner) && !recipe.getOwner().equals("public")) throw new RecipeNotFoundException(body.getIdRecipe());

        Menu menu = getMenu(body.getIdMenu(), owner);

        if(menu.getRecipes().stream().noneMatch(item -> item.getRecipe().getId().equals(body.getIdRecipe()))) {
            RecipeInMenu recipeInMenu = new RecipeInMenu(recipe, menu, body.getCategory());
            recipeInMenuRepository.save(recipeInMenu);
        }
    }

    public void removeRecipeFromMenu(String bearerToken, RecipeInMenuDto body) throws JSONException {
        String owner = JwtUtil.getSubjectFromToken(bearerToken);
        Menu menu = getMenu(body.getIdMenu(), owner);

        Optional<RecipeInMenu> recipeOptional = menu.getRecipes().stream().filter(item -> item.getRecipe().getId().equals(body.getIdRecipe())).findAny();
        if(recipeOptional.isEmpty()) throw new RecipeNotFoundException(body.getIdRecipe());

        RecipeInMenu recipeInMenu = recipeOptional.get();
        recipeInMenuRepository.deleteById(recipeInMenu.getId());
    }

    public Menu getMenu(Integer idMenu, String owner){
        Optional<Menu> optionalMenu = menuRepository.findById(idMenu);
        if(optionalMenu.isEmpty()) throw new MenuNotFoundException(idMenu);
        Menu menu = optionalMenu.get();
        if(!menu.getOwner().equals(owner)) throw new MenuNotFoundException(idMenu);

        return menu;
    }

    public void addToMenu(Menu menu, Recipe recipe, String category){
        RecipeInMenu recipeInMenu = new RecipeInMenu(recipe, menu, category);
        recipeInMenuRepository.save(recipeInMenu);
    }
}
