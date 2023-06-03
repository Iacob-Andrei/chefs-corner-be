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
@Table(name= "direction")
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_recipe")
    private Recipe recipe;
    private Integer step;
    private String instruction;
    private String video;

    public Direction(Recipe recipe, Integer order, String instruction) {
        this.recipe = recipe;
        this.step = order;
        this.instruction = instruction;
        this.video = "";
    }
}
