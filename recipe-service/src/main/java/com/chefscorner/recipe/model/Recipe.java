package com.chefscorner.recipe.model;

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
@Table(name= "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer prep_time;
    private Integer cook_time;
    private Integer number_servings;
    private String image;
    private String owner;

    @OneToMany(
            mappedBy = "recipe",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Direction> directions = new ArrayList<>();

    @OneToMany(
            mappedBy = "recipe",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Category> categories = new ArrayList<>();

    public Recipe(String name, Integer prep_time, Integer cook_time, Integer number_servings, String owner) {
        this.name = name;
        this.prep_time = prep_time;
        this.cook_time = cook_time;
        this.number_servings = number_servings;
        this.image = "";
        this.owner = owner;
    }
}
