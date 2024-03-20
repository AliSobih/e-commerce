package com.ecomerce.my.ECommerce.project.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class controllerAdvice {
    @ExceptionHandler
    public ResponseEntity<EmptyFieldExceptionResponse> emptyField(EmptyFieldException e) {
        EmptyFieldExceptionResponse response = new EmptyFieldExceptionResponse();
        response.message = e.getMessage();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
