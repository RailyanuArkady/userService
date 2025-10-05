package com.example.userservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record PassportCreateRequest(
        @Pattern(regexp = "\\d{4}", message = "Invalid passport series: must be 4 digits")
        @NotBlank String passportSeries,
        @Pattern(regexp = "\\d{6}", message = "Invalid passport number: must be 6 digits")
        @NotBlank String passportNumber,
        @NotBlank String passportDivisionName,
        @NotBlank String passportDivisionCode,
        @NotNull LocalDate passportDateOfIssue
) {
}