package com.chefscorner.recipe.controller;

import com.chefscorner.recipe.dto.CompleteMenuRequest;
import com.chefscorner.recipe.dto.MenuDto;
import com.chefscorner.recipe.service.MenuService;
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

    @PostMapping("")
    public ResponseEntity<?> postMenu(@RequestHeader("Authorization") String bearerToken, @RequestBody CompleteMenuRequest menuDto) throws JSONException {
        menuService.saveMenu(bearerToken, menuDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/owned")
    public ResponseEntity<List<MenuDto>> getUsersMenus(@RequestHeader("Authorization") String bearerToken) throws JSONException{
        return ResponseEntity.ok().body(menuService.getUsersMenus(bearerToken));
    }

    @GetMapping("/{idMenu}")
    public ResponseEntity<MenuDto> getMenuInfo(@RequestHeader("Authorization") String bearerToken, @PathVariable("idMenu")Integer idMenu) throws JSONException{
        return ResponseEntity.ok().body(menuService.getMenu(bearerToken, idMenu));
    }
}
