package com.example.simplecrudproject.exception;

public class UserNotFoundException extends RuntimeException {
//    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String text){
        super(text);
    }

}
