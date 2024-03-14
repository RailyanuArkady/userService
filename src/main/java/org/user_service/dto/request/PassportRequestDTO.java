package org.user_service.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PassportRequestDTO(
        @NotBlank
        @Valid
        @Pattern(regexp = "^\\d{4}$")
        @Size(min = 4, max = 4)
        String passportSeries,
        @NotBlank
        @Valid
        @Pattern(regexp = "^\\d{6}$")
        @Size(min = 6, max = 6)
        String passportNumber,
        @NotBlank
        @Valid
        String passportDivisionName,
        @NotBlank
        @Valid
        @Pattern(regexp = "^\\d{6}$")
        @Size(min = 6, max = 6)
        String passportDivisionCode,
        @NotNull
        LocalDate passportDateOfIssue) {

}
        
