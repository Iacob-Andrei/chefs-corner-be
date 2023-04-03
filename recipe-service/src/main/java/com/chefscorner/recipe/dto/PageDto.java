package com.chefscorner.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PageDto {
    private Integer currentPage;
    private Integer totalPages;
    private List<RecipeDto> recipes;
}
