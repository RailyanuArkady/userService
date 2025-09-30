package com.example.userservice.exception;

import com.example.userservice.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({PhoneAlreadyExistsException.class, EmailAlreadyExistsException.class,
            PassportAlreadyExistsException.class, Exception.class})
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        if (ex instanceof PhoneAlreadyExistsException) {
            return buildErrorResponse("PHONE_ALREADY_EXISTS", ex.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (ex instanceof EmailAlreadyExistsException) {
            return buildErrorResponse("EMAIL_ALREADY_EXISTS", ex.getMessage(), HttpStatus.BAD_REQUEST);
        } else {
            return buildErrorResponse("INTERNAL_SERVER_ERROR", "Internal server error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private ResponseEntity<ErrorResponse> buildErrorResponse(String errorCode, String message, HttpStatus status) {
        ErrorResponse error = new ErrorResponse(errorCode, message, LocalDateTime.now());
        return ResponseEntity.status(status).body(error);
    }
}