package com.chefscorner.recipe.controller.errorhandling;

import com.chefscorner.recipe.exception.IngredientNotFoundException;
import com.chefscorner.recipe.exception.InvalidNumberPage;
import com.chefscorner.recipe.exception.MenuNotFoundException;
import com.chefscorner.recipe.exception.RecipeForbiddenException;
import com.chefscorner.recipe.exception.RecipeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            InvalidNumberPage.class,
    })
    public ResponseEntity<String> invalidRequestParameterExceptionHandler(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(ex.getMessage());
    }

    @ExceptionHandler({
            RecipeNotFoundException.class,
            IngredientNotFoundException.class,
            MenuNotFoundException.class
    })
    public ResponseEntity<String> notFoundExceptionHandler(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(ex.getMessage());
    }

    @ExceptionHandler({
            RecipeForbiddenException.class
    })
    public ResponseEntity<String> forbiddenExceptionHandler(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(ex.getMessage());
    }
}
