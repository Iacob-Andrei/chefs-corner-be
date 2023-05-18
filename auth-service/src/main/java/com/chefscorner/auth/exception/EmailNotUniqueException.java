package com.chefscorner.auth.exception;

public class EmailNotUniqueException extends RuntimeException{
    public EmailNotUniqueException(){
        super("Email already in use!");
    }
}
