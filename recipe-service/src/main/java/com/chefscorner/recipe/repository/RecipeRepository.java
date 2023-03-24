package com.chefscorner.recipe.repository;

import com.chefscorner.recipe.model.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
