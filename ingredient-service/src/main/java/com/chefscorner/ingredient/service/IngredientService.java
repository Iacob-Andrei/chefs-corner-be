package com.chefscorner.ingredient.service;

import com.chefscorner.ingredient.dto.IngredientDto;
import com.chefscorner.ingredient.mapper.IngredientMapper;
import com.chefscorner.ingredient.model.Ingredient;
import com.chefscorner.ingredient.repository.IngredientPriceRepository;
import com.chefscorner.ingredient.repository.IngredientRepository;
import com.chefscorner.ingredient.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientPriceRepository ingredientPriceRepository;

    public List<IngredientDto> findIngredientsByNamePattern(String pattern) {
        List<Ingredient> result = ingredientRepository.findByNameContainingIgnoreCase(pattern);
        result.sort(Comparator.comparingInt((Ingredient i) -> i.getName().length()));

        int max = Math.min(result.size(), 10);
        return result.subList(0,max).stream().map(IngredientMapper::ingredientWithInfoOnly).collect(Collectors.toList());
    }


    public List<IngredientDto> getPricesForIngredientOfUser(String bearerToken) throws JSONException {
        String owner = JwtUtil.getSubjectFromToken(bearerToken);
        List<Ingredient> ingredients = ingredientPriceRepository.findPricesForUser(owner);

        for(Ingredient ingredient : ingredients){
            ingredient.setIngredientPrices(
                    ingredient.getIngredientPrices().stream().filter(price -> price.getOwner().equals(owner)).collect(Collectors.toList())
            );
        }

        return ingredients.stream().map(IngredientMapper::ingredientWithPrices).collect(Collectors.toList());
    }
}
