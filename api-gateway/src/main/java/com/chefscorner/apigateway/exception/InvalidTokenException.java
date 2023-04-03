package com.chefscorner.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTokenException extends ResponseStatusException {
    public InvalidTokenException(){
        super(HttpStatus.UNAUTHORIZED, "Invalid token!");
    }
}

