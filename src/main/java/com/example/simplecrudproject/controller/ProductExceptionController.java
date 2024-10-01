package com.example.simplecrudproject.controller;

import com.example.simplecrudproject.exception.ProductNotFoundException;
import com.example.simplecrudproject.model.Exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductExceptionController {

    @ExceptionHandler(value = ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productNotFoundException(Model model){
        Exceptions exception = new Exceptions("Product Not Found");
        model.addAttribute("exception", exception);
        return "/exception";
    }

}


