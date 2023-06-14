package com.chefscorner.recipe.repository;

import com.chefscorner.recipe.model.Direction;
import com.chefscorner.recipe.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DirectionRepository extends JpaRepository<Direction, Integer> {

    @Query(value = "select d from Direction d join Recipe r on d.recipe = r and r.id = ?1")
    List<Direction> findByRecipeId(Integer id);

    @Query(value = "select d from Direction d where d.step = ?1 and d.recipe.id = ?2")
    Optional<Direction> findByOrderAndRecipe(Integer order, Integer idRecipe);

    void deleteDirectionsByRecipe(Recipe recipe);
}
