package com.chefscorner.recipe.repository;

import com.chefscorner.recipe.model.Category;
import com.chefscorner.recipe.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c.recipe FROM Category c WHERE lower(c.category) = COALESCE(:categoryParam, c.category) ORDER BY c.recipe.id")
    Page<Recipe> findByCategory(@Param("categoryParam") String categoryParam, Pageable page);

    @Query("SELECT DISTINCT(c.recipe) FROM Category c")
    Page<Recipe> findByPage(Pageable page);

    @Query("SELECT DISTINCT(c.recipe) FROM Category c WHERE lower(trim(c.category)) = lower(trim(?1)) AND c.id <= 9565")
    List<Recipe> findCategory(String category);
}
