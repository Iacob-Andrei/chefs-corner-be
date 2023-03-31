package com.chefscorner.ingredient.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Float price_per_unit;

    private String image;

    private String svg;

    @OneToMany(
            mappedBy = "ingredient",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<IngredientToRecipe> ingredientToRecipeList = new ArrayList<>();

    public Ingredient(Integer id, String name, String image, String svg) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.svg = svg;
    }
}
