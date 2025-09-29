package com.example.userservice.dto;


import java.time.LocalDateTime;

public record ErrorResponse(
        String error,
        String description,
        LocalDateTime dateTime
) {}
