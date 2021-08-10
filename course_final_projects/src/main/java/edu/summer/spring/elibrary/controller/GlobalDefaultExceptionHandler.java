package edu.summer.spring.elibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public String exception(Exception e) {
        return "404";
    }
}
