package com.chefscorner.auth.exception;

public class EmailNotConfirmedException extends RuntimeException{
    public EmailNotConfirmedException(){
        super("Email not confirmed!");
    }
}
