package com.example.userservice.dto.projection;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;

@JsonPropertyOrder({"passportSeries", "passportNumber", "passportDivisionName",
        "passportDivisionCode", "passportDateOfIssue"})
public interface PassportProjection {
    String getPassportSeries();

    String getPassportNumber();

    String getPassportDivisionName();

    String getPassportDivisionCode();

    LocalDate getPassportDateOfIssue();
}
