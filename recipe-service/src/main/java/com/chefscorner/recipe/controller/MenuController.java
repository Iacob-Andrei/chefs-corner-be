package com.chefscorner.recipe.controller;

import com.chefscorner.recipe.dto.CompleteMenuRequest;
import com.chefscorner.recipe.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/menu")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("")
    public ResponseEntity<String> postMenu(@RequestHeader("Authorization") String bearerToken, @RequestBody CompleteMenuRequest menuDto) throws JSONException {
        menuService.saveMenu(bearerToken, menuDto);
        return ResponseEntity.ok().body("ok");
    }
}
