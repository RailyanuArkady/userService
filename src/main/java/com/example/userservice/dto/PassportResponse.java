package com.example.userservice.dto;

import java.time.LocalDate;

public record PassportResponse(
        String passportSeries,
        String passportNumber,
        String passportDivisionName,
        String passportDivisionCode,
        LocalDate passportDateOfIssue

) {
}
