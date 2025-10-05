package com.example.userservice.exception;

public class PassportAlreadyExistsException extends RuntimeException {
    public PassportAlreadyExistsException(String message) {
        super(message);
    }
}
