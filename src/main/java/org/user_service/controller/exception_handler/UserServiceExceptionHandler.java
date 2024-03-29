package org.user_service.controller.exception_handler;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.user_service.dto.response.ErrorResponseDTO;

import java.time.LocalDateTime;

@RestControllerAdvice
public class UserServiceExceptionHandler {

    @ApiResponse(responseCode = "400", description = "Validation error",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponseDTO.class))})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ErrorResponseDTO handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(),
                e.getMessage(), LocalDateTime.now());
    }

    @ApiResponse(responseCode = "500",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponseDTO.class))})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    private ErrorResponseDTO handleServerException(Exception e) {
        return new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.getMessage(), LocalDateTime.now());
    }

    @ApiResponse(responseCode = "404", description = "User no found",
            content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ErrorResponseDTO.class))})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    private ErrorResponseDTO handleNotFoundException() {
        return new ErrorResponseDTO(HttpStatus.NOT_FOUND.value(),
                "Entity not found or marked as Deleted", LocalDateTime.now());
    }
}
