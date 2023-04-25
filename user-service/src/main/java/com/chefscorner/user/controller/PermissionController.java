package com.chefscorner.user.controller;

import com.chefscorner.user.dto.PermissionDto;
import com.chefscorner.user.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
