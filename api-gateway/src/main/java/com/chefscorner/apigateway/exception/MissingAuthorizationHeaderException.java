package com.chefscorner.apigateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MissingAuthorizationHeaderException extends ResponseStatusException {
    public MissingAuthorizationHeaderException(){
        super(HttpStatus.UNAUTHORIZED, "Missing authorization header!");
    }
}
