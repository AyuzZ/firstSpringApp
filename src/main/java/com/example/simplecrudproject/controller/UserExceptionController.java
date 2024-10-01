package com.example.simplecrudproject.controller;

import com.example.simplecrudproject.exception.UserAlreadyExistsException;
import com.example.simplecrudproject.exception.UserNotFoundException;
import com.example.simplecrudproject.model.Exceptions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionController {

    @ExceptionHandler(value = UserNotFoundException.class)
    public String userNotFoundException(Model model){
        Exceptions exception = new Exceptions("User Account Not Found!");
        model.addAttribute("exception", exception);
        return "/exception";
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public String userExistsException(Model model){
        Exceptions exception = new Exceptions("User Account Already Exists! Try New Username");
        model.addAttribute("exception", exception);
        return "/exception";
    }

}


