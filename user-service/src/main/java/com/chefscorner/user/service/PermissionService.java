package com.chefscorner.user.service;

import com.chefscorner.user.dto.PermissionDto;
import com.chefscorner.user.model.Permission;
import com.chefscorner.user.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository repository;

    public void addUserPermission(PermissionDto permissionDto) {
        Optional<Permission> optional = repository.findPermissionByEmailAndIdRecipe(permissionDto.getEmail(), permissionDto.getIdRecipe());
        if(optional.isPresent()) return;

        Permission permission = new Permission(permissionDto.getIdRecipe(), permissionDto.getEmail());
        repository.save(permission);
    }

    public List<String> getUsersWithPermission(Integer idRecipe) {
        return repository.findPermissionsByIdRecipe(idRecipe).stream().map(Permission::getEmail).collect(Collectors.toList());
    }
}
