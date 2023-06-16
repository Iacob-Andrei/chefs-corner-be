package com.chefscorner.recipe.controller;

import com.chefscorner.recipe.dto.CompleteMenuRequest;
import com.chefscorner.recipe.dto.MenuDto;
import com.chefscorner.recipe.dto.RecipeInMenuDto;
import com.chefscorner.recipe.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/menu")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "Post a menu.")
    @PostMapping("")
    public ResponseEntity<?> postMenu(@RequestHeader("Authorization") String bearerToken, @RequestBody CompleteMenuRequest menuDto) throws JSONException {
        menuService.saveMenu(bearerToken, menuDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete menu.")
    @DeleteMapping("/{idMenu}")
    public ResponseEntity<?> removeMenu(@RequestHeader("Authorization") String bearerToken,
                                        @PathVariable("idMenu")Integer idMenu) throws JSONException{
        menuService.removeMenu(bearerToken, idMenu);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Find menus of owner.")
    @GetMapping("/owned")
    public ResponseEntity<List<MenuDto>> getUsersMenus(@RequestHeader("Authorization") String bearerToken) throws JSONException{
        return ResponseEntity.ok().body(menuService.getUsersMenus(bearerToken));
    }

    @Operation(summary = "Get menu information.")
    @GetMapping("/{idMenu}")
    public ResponseEntity<MenuDto> getMenuInfo(@RequestHeader("Authorization") String bearerToken, @PathVariable("idMenu")Integer idMenu) throws JSONException{
        return ResponseEntity.ok().body(menuService.getMenu(bearerToken, idMenu));
    }

    @Operation(summary = "Add recipe to menu.")
    @PostMapping("/add")
    public ResponseEntity<?> addRecipeToMenu(@RequestHeader("Authorization") String bearerToken,
                                             @RequestBody RecipeInMenuDto body) throws JSONException {
        menuService.addRecipeToMenu(bearerToken, body);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove recipe from menu.")
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeRecipeFromMenu(@RequestHeader("Authorization") String bearerToken,
                                                  @RequestBody RecipeInMenuDto body) throws JSONException {
        menuService.removeRecipeFromMenu(bearerToken, body);
        return ResponseEntity.ok().build();
    }
}
