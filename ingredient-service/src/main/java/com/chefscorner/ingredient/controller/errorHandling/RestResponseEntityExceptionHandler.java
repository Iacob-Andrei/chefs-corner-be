package com.chefscorner.ingredient.controller.errorHandling;

import com.chefscorner.ingredient.exception.IngredientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            IngredientNotFoundException.class
    })

    public ResponseEntity<String> invalidRequestParameterExceptionMapper(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(ex.getMessage());
    }
}