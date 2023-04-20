package com.chefscorner.ingredient.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "ingredient_to_recipe")
public class IngredientToRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer id_recipe;

    @ManyToOne
    @JoinColumn(name = "id_ingredient")
    private Ingredient ingredient;

    private Double amount;

    private String description;

    private String unit;

    private Double grams;

    public IngredientToRecipe(Integer id_recipe, Ingredient ingredient, Double amount, String description, String unit, Double grams) {
        this.id_recipe = id_recipe;
        this.ingredient = ingredient;
        this.amount = amount;
        this.description = description;
        this.unit = unit;
        this.grams = grams;
    }
}
