package com.chefscorner.auth.controller.errorHandling;

import com.chefscorner.auth.exception.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityAccessDeniedHandler {
    @ExceptionHandler({
            InvalidTokenException.class
    })

    public ResponseEntity<ErrorMessage> invalidRequestParameterExceptionMapper(RuntimeException ex) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(errorMessage);
    }
}
