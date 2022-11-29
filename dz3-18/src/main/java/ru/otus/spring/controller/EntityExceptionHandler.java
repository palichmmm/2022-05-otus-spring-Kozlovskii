package ru.otus.spring.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class EntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleNPE(RuntimeException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }
}
