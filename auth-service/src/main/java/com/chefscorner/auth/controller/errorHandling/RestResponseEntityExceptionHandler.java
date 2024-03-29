package com.chefscorner.auth.controller.errorHandling;

import com.chefscorner.auth.exception.EmailNotConfirmedException;
import com.chefscorner.auth.exception.EmailNotUniqueException;
import com.chefscorner.auth.exception.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            EmailNotUniqueException.class
    })
    public ResponseEntity<String> invalidRequestParameterExceptionMapper(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(ex.getMessage());
    }

    @ExceptionHandler({
            InvalidTokenException.class,
            EmailNotConfirmedException.class
    })
    public ResponseEntity<String> invalidTokenException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(ex.getMessage());
    }
}
