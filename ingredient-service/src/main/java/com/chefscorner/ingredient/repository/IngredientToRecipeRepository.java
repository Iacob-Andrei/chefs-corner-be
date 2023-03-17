package com.chefscorner.ingredient.repository;

import com.chefscorner.ingredient.model.IngredientToRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientToRecipeRepository extends JpaRepository<IngredientToRecipe, Integer> {

    @Query(value = "select i from IngredientToRecipe i where i.id_recipe = ?1")
    List<IngredientToRecipe> findIngredientsByIdRecipe(Integer id);
}