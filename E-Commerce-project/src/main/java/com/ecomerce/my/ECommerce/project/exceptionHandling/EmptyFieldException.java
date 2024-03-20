package com.ecomerce.my.ECommerce.project.exceptionHandling;

public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException(String message) {
        super(message);
    }
}
