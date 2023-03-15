package com.chefscorner.recipe.mapper;

import com.chefscorner.recipe.dto.CategoryDto;
import com.chefscorner.recipe.model.Category;

public class CategoryMapper {

    public static CategoryDto categoryToCategoryDto(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .category(category.getCategory())
                .build();
    }
}
