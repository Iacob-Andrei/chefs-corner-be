package com.chefscorner.recipe.exception;

public class InvalidNumberPage extends RuntimeException{
    public InvalidNumberPage(Integer page, String category){
        super(String.format("Negative page (%d) number for category '%s'.", page, category));
    }

    public InvalidNumberPage(String category){
        super(String.format("Invalid category type '%s'.", category));
    }

    public InvalidNumberPage(Integer page, Integer totalPages, String category){
        super(String.format("Invalid page (%d/%d) number for category '%s'.", page, totalPages, category));
    }
}
