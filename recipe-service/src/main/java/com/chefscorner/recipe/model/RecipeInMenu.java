package com.chefscorner.recipe.model;

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
@Table(name= "recipe_in_menu")
public class RecipeInMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_recipe")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "id_menu")
    private Menu menu;

    private String category;

    public RecipeInMenu(Recipe recipe, Menu menu, String category) {
        this.recipe = recipe;
        this.menu = menu;
        this.category = category;
    }
}
