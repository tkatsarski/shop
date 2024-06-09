package com.example.shop.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> exception(ResourceNotFoundException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        
        return new ResponseEntity<>(new ErrorResponse(status, e.getMessage()), status);
    }
}
