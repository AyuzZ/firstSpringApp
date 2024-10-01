package com.example.simplecrudproject.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "/accessDenied";
    }

    @RequestMapping("products/error")
    public String errorHandler(HttpServletRequest request){
        return "/error";
    }

}
