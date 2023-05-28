package com.chefscorner.user.exception;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException(){
        super("Token expired!");
    }
}
