package com.chefscorner.ingredient.service;

import com.chefscorner.ingredient.dto.IngredientDto;
import com.chefscorner.ingredient.mapper.IngredientMapper;
import com.chefscorner.ingredient.model.Ingredient;
import com.chefscorner.ingredient.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public List<IngredientDto> findIngredientsByNamePattern(String pattern) {
        List<Ingredient> result = ingredientRepository.findByNameContainingIgnoreCase(pattern);
        result.sort(Comparator.comparingInt((Ingredient i) -> i.getName().length()));

        int max = Math.min(result.size(), 10);
        return result.subList(0,max).stream().map(IngredientMapper::ingredientWithInfoOnly).collect(Collectors.toList());
    }
}
