package com.chefscorner.recipe.exception;

public class IngredientNotFoundException extends RuntimeException{
    public IngredientNotFoundException(String msg){
        super(msg);
    }
}
