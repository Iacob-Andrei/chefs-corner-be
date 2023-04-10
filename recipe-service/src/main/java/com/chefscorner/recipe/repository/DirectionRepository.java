package com.chefscorner.recipe.repository;

import com.chefscorner.recipe.model.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {

    @Query(value = "select d from Direction d join Recipe r on d.recipe = r and r.id = ?1")
    List<Direction> findByRecipeId(Integer id);
}
