package com.chefscorner.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DirectionNotFoundException extends RuntimeException{
    public DirectionNotFoundException(Integer recipeId, Integer order){
        super("Direction with order " + order + " from recipe " + recipeId + " not found.");
    }
}