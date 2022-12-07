package ru.otus.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.otus.spring.models.ErrorMessage;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@RestControllerAdvice
public class EntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> notFoundException(RuntimeException exception) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_FOUND.value(), Map.of("error", exception.getMessage())), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> validationError(ConstraintViolationException exception) {
        Map<String, String> message = new HashMap<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            message.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_ACCEPTABLE.value(), message), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> validationError(MethodArgumentNotValidException exception) {
        Map<String, String> message = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            message.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.NOT_ACCEPTABLE.value(), message), HttpStatus.NOT_ACCEPTABLE);
    }
}