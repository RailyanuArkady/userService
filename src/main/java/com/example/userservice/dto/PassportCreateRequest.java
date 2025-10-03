package com.example.userservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PassportCreateRequest(
        @NotBlank String passportSeries,
        @NotBlank String passportNumber,
        @NotBlank String passportDivisionName,
        @NotBlank String passportDivisionCode,
        @NotNull LocalDate passportDateOfIssue
) {
}