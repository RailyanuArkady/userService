package com.example.userservice.dto.response;


import java.time.LocalDateTime;

public record ErrorResponse(
        String error,
        String description,
        LocalDateTime dateTime
) {
}
