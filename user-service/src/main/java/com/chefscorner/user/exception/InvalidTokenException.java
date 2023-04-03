package com.chefscorner.user.exception;

public class InvalidTokenException extends RuntimeException{
    public InvalidTokenException(){
        super("Invalid token. Access denied.");
    }
}
