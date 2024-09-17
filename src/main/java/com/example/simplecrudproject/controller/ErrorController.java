package com.example.simplecrudproject.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("/products/error")
    public String errorHandler(HttpServletRequest request){
        return "products/error";
    }
}
