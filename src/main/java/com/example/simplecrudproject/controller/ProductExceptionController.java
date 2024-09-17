package com.example.simplecrudproject.controller;

import com.example.simplecrudproject.exception.ProductNotFoundException;
import com.example.simplecrudproject.model.Exceptions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionController {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public String productNotFoundException(ProductNotFoundException exception, Model model){
        Exceptions errorMessage = new Exceptions("Product Not Found");
        model.addAttribute("errorMessage", errorMessage);
        return "products/exception";
    }
//    public ResponseEntity<Object> productNotFoundException(ProductNotFoundException exception){
//        return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
//    }



}


