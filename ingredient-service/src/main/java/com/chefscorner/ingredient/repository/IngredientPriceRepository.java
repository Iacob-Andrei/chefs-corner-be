package com.chefscorner.ingredient.repository;

import com.chefscorner.ingredient.model.Ingredient;
import com.chefscorner.ingredient.model.IngredientPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientPriceRepository extends JpaRepository<IngredientPrice, Integer> {
    @Query(value = "SELECT distinct (i.ingredient) FROM IngredientPrice i WHERE i.owner = ?1")
    List<Ingredient> findPricesForUser(String owner);

    void deleteIngredientPriceById(Integer id);
}
