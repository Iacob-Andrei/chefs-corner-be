package com.chefscorner.user.exception;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException(String email){
        super(String.format("Email does not exist : %s.", email));
    }
}
