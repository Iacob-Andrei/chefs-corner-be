package com.chefscorner.ingredient.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class IngredientPriceDto {
    private Integer id;
    private String seller;
    private Double price;
    private String owner;
    private Integer idIngredient;
}
