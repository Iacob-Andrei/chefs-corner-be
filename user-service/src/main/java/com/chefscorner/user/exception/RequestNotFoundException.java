package com.chefscorner.user.exception;

public class RequestNotFoundException extends RuntimeException{
    public RequestNotFoundException(){
        super("Request not found!");
    }
}
