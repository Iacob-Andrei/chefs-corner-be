package com.chefscorner.recipe.exception;

public class RecipeForbiddenException extends RuntimeException{
    public RecipeForbiddenException(){
        super("Recipe forbidden.");
    }
}
