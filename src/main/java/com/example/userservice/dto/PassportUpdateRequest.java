package com.example.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record PassportUpdateRequest(
        @NotBlank
        @Pattern(regexp = "\\d{4}", message = "Invalid passport series: must be 4 digits")
        String passportSeries,
        @NotBlank
        @Pattern(regexp = "\\d{6}", message = "Invalid passport number: must be 6 digits")
        String passportNumber,
        @NotBlank
        String passportDivisionName,
        @NotBlank
        String passportDivisionCode,
        @NotNull
        LocalDate passportDateOfIssue
) {
}
