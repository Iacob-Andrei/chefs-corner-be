package com.chefscorner.ingredient.mapper;

import com.chefscorner.ingredient.dto.IngredientDto;
import com.chefscorner.ingredient.dto.IngredientPriceDto;
import com.chefscorner.ingredient.model.Ingredient;
import com.chefscorner.ingredient.model.IngredientPrice;

public class IngredientMapper {

    public static IngredientDto ingredientToIngredientDtoWithImages(Ingredient ingredient){
        return IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .price_per_unit(ingredient.getPrice_per_unit())
                .image(ingredient.getImage())
                .svg(ingredient.getSvg())
                .build();
    }

    public static IngredientDto ingredientWithInfoOnly(Ingredient ingredient){
        return  IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .build();
    }

    public static IngredientPriceDto priceToPriceDto(IngredientPrice price){
        return IngredientPriceDto.builder()
                .id(price.getId())
                .seller(price.getSeller())
                .price(price.getPrice())
                .owner(price.getOwner())
                .build();
    }
}
