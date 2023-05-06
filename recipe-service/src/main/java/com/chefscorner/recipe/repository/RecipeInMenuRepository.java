package com.chefscorner.recipe.repository;

import com.chefscorner.recipe.model.RecipeInMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeInMenuRepository extends JpaRepository<RecipeInMenu, Integer> {
}
