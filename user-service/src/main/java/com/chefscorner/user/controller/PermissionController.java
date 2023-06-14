package com.chefscorner.user.controller;

import com.chefscorner.user.dto.PermissionDto;
import com.chefscorner.user.dto.RequestPermissionDto;
import com.chefscorner.user.dto.ResponseDto;
import com.chefscorner.user.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Add access permission for user.")
    @PostMapping("/add")
    public ResponseEntity<?> addUserPermission(@RequestBody PermissionDto permissionDto){
        permissionService.addUserPermission(permissionDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Find users that have permission on recipe.")
    @GetMapping("/{idRecipe}")
    public ResponseEntity<List<String>> getUsersWithPermission(@PathVariable("idRecipe")Integer idRecipe){
        return ResponseEntity.ok().body(permissionService.getUsersWithPermission(idRecipe));
    }

    @Operation(summary = "Find recipes with permission for user.")
    @GetMapping("/for-user/{email}")
    public ResponseEntity<List<Integer>> getRecipesWithPermission(@PathVariable("email")String email){
        return ResponseEntity.ok().body(permissionService.getRecipesWithPermission(email));
    }

    @Operation(summary = "Remove user permission on recipe.")
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeUserPermission(@RequestBody PermissionDto permissionDto) {
        permissionService.removeUserPermission(permissionDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get request access information.")
    @GetMapping("/request")
    public ResponseEntity<RequestPermissionDto> getRequestPermissionInfo(@RequestHeader("Authorization") String bearerToken, @RequestParam("token")String token) throws JSONException {
        return ResponseEntity.ok().body(permissionService.getRequestPermissionInfo(bearerToken, token));
    }

    @Operation(summary = "Request access permission for recipe.")
    @GetMapping("/ask/{id}")
    public ResponseEntity<ResponseDto> askPermissionForRecipe(@RequestHeader("Authorization") String bearerToken, @PathVariable Integer id) throws JSONException {
        return ResponseEntity.ok().body(permissionService.askPermissionForRecipe(bearerToken, id));
    }

    @Operation(summary = "Accept request access.")
    @GetMapping("/confirm")
    public ResponseEntity<ResponseDto> confirmPermissionForRecipe(@RequestHeader("Authorization") String bearerToken, @RequestParam("token")String token) throws JSONException {
        return ResponseEntity.ok().body(permissionService.confirmPermissionForRecipe(bearerToken, token));
    }

    @Operation(summary = "Deny request access.")
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deletePermissionRequestForRecipe(@RequestHeader("Authorization") String bearerToken, @RequestParam("token")String token) throws JSONException {
        return ResponseEntity.ok().body(permissionService.deletePermissionRequestForRecipe(bearerToken, token));
    }
}
