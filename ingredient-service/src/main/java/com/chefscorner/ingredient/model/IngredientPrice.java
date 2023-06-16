package com.chefscorner.ingredient.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "ingredient_price")
public class IngredientPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String owner;
    private String seller;
    private Double price;
    @ManyToOne
    @JoinColumn(name = "id_ingredient")
    private Ingredient ingredient;

    public IngredientPrice(String owner, String seller, Double price, Ingredient ingredient) {
        this.owner = owner;
        this.seller = seller;
        this.price = price;
        this.ingredient = ingredient;
    }
}
