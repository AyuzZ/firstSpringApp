package com.example.simplecrudproject.exception;

public class ProductNotFoundException extends RuntimeException {
//    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String text){
        super(text);
    }

}
