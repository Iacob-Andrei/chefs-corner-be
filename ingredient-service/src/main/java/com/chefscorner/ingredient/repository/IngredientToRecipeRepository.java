package com.chefscorner.ingredient.repository;

import com.chefscorner.ingredient.model.IngredientToRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientToRecipeRepository extends JpaRepository<IngredientToRecipe, Integer> {

    @Query(value = "select i from IngredientToRecipe i where i.recipe = ?1")
    List<IngredientToRecipe> findIngredientsByIdRecipe(Integer id);

    List<IngredientToRecipe> findIngredientToRecipesByRecipeIsIn(List<Integer> list);

    @Query(value = "SELECT i.recipe from IngredientToRecipe i WHERE i.ingredient.id IN ?1 GROUP BY i.recipe HAVING COUNT(DISTINCT i.ingredient.id) >= ?2")
    List<Integer> findRecipesContainingIngredients(List<Integer> ids, Integer size);

    void deleteIngredientToRecipesByRecipe(Integer recipe);
}
