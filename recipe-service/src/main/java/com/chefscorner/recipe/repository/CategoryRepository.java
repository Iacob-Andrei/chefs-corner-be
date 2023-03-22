package com.chefscorner.recipe.repository;

import com.chefscorner.recipe.model.Category;
import com.chefscorner.recipe.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c.recipe FROM Category c WHERE c.category = COALESCE(:categoryParam, c.category)")
    Page<Recipe> findByCategory(@Param("categoryParam") String categoryParam, Pageable page);
}
