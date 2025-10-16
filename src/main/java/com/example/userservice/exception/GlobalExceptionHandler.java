package com.example.userservice.exception;

import com.example.userservice.dto.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return buildErrorResponse("VALIDATION_ERROR", errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhoneAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlePhoneAlreadyExists(PhoneAlreadyExistsException ex) {
        return buildErrorResponse("PHONE_ALREADY_EXISTS", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        return buildErrorResponse("EMAIL_ALREADY_EXISTS", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PassportAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handlePassportAlreadyExists(PassportAlreadyExistsException ex) {
        return buildErrorResponse("PASSPORT_ALREADY_EXISTS", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        return buildErrorResponse("INTERNAL_SERVER_ERROR", "Internal server error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidDataFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDataFormat(InvalidDataFormatException ex) {
        return buildErrorResponse("INVALID_DATA_FORMAT", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return buildErrorResponse("USER_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String errorCode, String message, HttpStatus status) {
        ErrorResponse error = new ErrorResponse(errorCode, message, LocalDateTime.now());
        return ResponseEntity.status(status).body(error);
    }
}