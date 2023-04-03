package com.chefscorner.recipe.service;

import com.chefscorner.recipe.dto.PageDto;
import com.chefscorner.recipe.exception.InvalidNumberPage;
import com.chefscorner.recipe.mapper.RecipeMapper;
import com.chefscorner.recipe.model.Recipe;
import com.chefscorner.recipe.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public PageDto getRecipesFromCategories(String category, Integer page) {
        if(page < 0){ throw new InvalidNumberPage(page, category); }

        Page<Recipe> slice = category.equals("") ? categoryRepository.findByPage(PageRequest.of(page, 20)) :
                                                   categoryRepository.findByCategory(category, PageRequest.of(page, 20));
        List<Recipe> recipesInBatch = slice.getContent();

        if(slice.getTotalPages() == 0){ throw new InvalidNumberPage(category); }
        if(recipesInBatch.size() == 0){ throw new InvalidNumberPage(page, slice.getTotalPages()-1,category); }

        return PageDto.builder()
                .currentPage(page)
                .totalPages(slice.getTotalPages()-1)
                .recipes(recipesInBatch.stream().map(RecipeMapper::recipeToRecipeDtoOnlyInfo).collect(Collectors.toList()))
                .build();
    }
}
