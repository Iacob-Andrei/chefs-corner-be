package com.chefscorner.recipe.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private String image;
    private List<DirectionDto> directions;
    private List<CategoryDto> categories;
    private List<IngredientInRecipeDto> ingredients;
}
