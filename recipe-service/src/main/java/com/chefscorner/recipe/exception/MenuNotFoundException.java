package com.chefscorner.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MenuNotFoundException extends RuntimeException{
    public MenuNotFoundException(Integer idMenu){
        super("Menu with id " + idMenu + " not found");
    }
}
