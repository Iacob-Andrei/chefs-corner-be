package com.chefscorner.user.controller.errorHandling;

import com.chefscorner.user.exception.EmailNotFoundException;
import com.chefscorner.user.exception.RequestNotFoundException;
import com.chefscorner.user.exception.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            EmailNotFoundException.class,
            TokenExpiredException.class
    })
    public ResponseEntity<String> invalidRequestParameterExceptionMapper(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(ex.getMessage());
    }

    @ExceptionHandler({
            RequestNotFoundException.class
    })
    public ResponseEntity<String> notFoundExceptionMapper(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(ex.getMessage());
    }
}
