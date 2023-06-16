package com.chefscorner.user.repository;

import com.chefscorner.user.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    Optional<Permission> findPermissionByEmailAndIdRecipe(String email, Integer idRecipe);
    List<Permission> findPermissionsByIdRecipe(Integer idRecipe);
    List<Permission> findPermissionsByEmail(String email);
    void deletePermissionByIdRecipeAndEmail(Integer id, String email);
}
