package com.chefscorner.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RecipeDto {

    private Integer id;
    private String name;
    private Integer prep_time;
    private Integer cook_time;
    private Integer number_servings;
}
