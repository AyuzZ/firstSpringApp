package com.example.simplecrudproject.exception;

public class UserAlreadyExistsException extends RuntimeException {
//    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(String text){
        super(text);
    }

}
