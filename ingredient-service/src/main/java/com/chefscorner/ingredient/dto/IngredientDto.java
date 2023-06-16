package com.chefscorner.ingredient.dto;

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
public class IngredientDto {

    private Integer id;
    private String name;
    private Float price_per_unit;
    private String image;
    private String svg;
    private List<IngredientPriceDto> prices;
}
