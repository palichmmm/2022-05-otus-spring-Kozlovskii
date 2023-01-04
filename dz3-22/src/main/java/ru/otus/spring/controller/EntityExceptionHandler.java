package ru.otus.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class EntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Mono<ResponseEntity<Object>> notFoundException(RuntimeException exception) {
        return Mono.just(new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST));
    }
}