package com.chefscorner.recipe.model;


import com.chefscorner.recipe.dto.DirectionDto;
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
    @Column(name = "video_data", columnDefinition = "BLOB")
    private byte[] video_data;

    public Direction(Recipe recipe, Integer order, String instruction) {
        this.recipe = recipe;
        this.step = order;
        this.instruction = instruction;
        this.video_data = new byte[0];
    }
}
