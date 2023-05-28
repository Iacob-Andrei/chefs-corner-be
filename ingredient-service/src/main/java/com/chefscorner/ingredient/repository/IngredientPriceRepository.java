package com.chefscorner.ingredient.repository;

import com.chefscorner.ingredient.model.IngredientPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientPriceRepository extends JpaRepository<IngredientPrice, Integer> {
}
