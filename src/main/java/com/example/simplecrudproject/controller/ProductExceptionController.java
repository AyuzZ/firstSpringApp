package com.example.simplecrudproject.controller;

import com.example.simplecrudproject.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionController {

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<Object> productNotFoundException(ProductNotFoundException exception){
        return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
    }

}
