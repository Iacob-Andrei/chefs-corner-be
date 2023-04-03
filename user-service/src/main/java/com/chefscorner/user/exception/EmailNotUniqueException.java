package com.chefscorner.user.exception;

public class EmailNotUniqueException extends RuntimeException{
    public EmailNotUniqueException(String email){
        super(String.format("Email already in use : %s.", email));
    }
}
