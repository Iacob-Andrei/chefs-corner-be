package com.chefscorner.ingredient.exception;

public class IngredientNotFoundException extends RuntimeException{
    public IngredientNotFoundException(String name){
        super("Ingredient with name " + name + " not found!");
    }
}
