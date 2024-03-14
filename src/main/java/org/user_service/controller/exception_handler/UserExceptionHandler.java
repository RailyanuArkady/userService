package org.user_service.controller.exception_handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.user_service.util.ValidationExceptionBody;

import java.time.LocalDateTime;

@RestControllerAdvice
public class UserExceptionHandler {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ValidationExceptionBody handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return new ValidationExceptionBody(HttpStatus.BAD_REQUEST.value(), e.getMessage(), LocalDateTime.now());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    private ValidationExceptionBody handleServerException(Exception e) {
        return new ValidationExceptionBody(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(), LocalDateTime.now());
    }

}
