package com.chefscorner.user.controller;

import com.chefscorner.user.dto.PermissionDto;
import com.chefscorner.user.dto.ResponseDto;
import com.chefscorner.user.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/permission")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping("/add")
    public void addUserPermission(@RequestBody PermissionDto permissionDto){
        permissionService.addUserPermission(permissionDto);
    }

    @GetMapping("/{idRecipe}")
    public ResponseEntity<List<String>> getUsersWithPermission(@PathVariable("idRecipe")Integer idRecipe){
        return ResponseEntity.ok().body(permissionService.getUsersWithPermission(idRecipe));
    }

    @GetMapping("/for-user/{email}")
    public ResponseEntity<List<Integer>> getRecipesWithPermission(@PathVariable("email")String email){
        return ResponseEntity.ok().body(permissionService.getRecipesWithPermission(email));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeUserPermission(@RequestBody PermissionDto permissionDto) {
        permissionService.removeUserPermission(permissionDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ask/{id}")
    public ResponseEntity<ResponseDto> askPermissionForRecipe(@RequestHeader("Authorization") String bearerToken, @PathVariable Integer id) throws JSONException {
        return ResponseEntity.ok().body(permissionService.askPermissionForRecipe(bearerToken, id));
    }

    @GetMapping("/confirm")
    public ResponseEntity<ResponseDto> confirmPermissionForRecipe(@RequestHeader("Authorization") String bearerToken, @RequestParam("token")String token) throws JSONException {
        return ResponseEntity.ok().body(permissionService.confirmPermissionForRecipe(bearerToken, token));
    }
}
