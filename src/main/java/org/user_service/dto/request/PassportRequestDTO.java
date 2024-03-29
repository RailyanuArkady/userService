package org.user_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.time.LocalDate;

public record PassportRequestDTO(
        @NotBlank
        @Pattern(regexp = "^\\d{4}$")
        String passportSeries,
        @NotBlank
        @Pattern(regexp = "^\\d{6}$")
        String passportNumber,
        @NotBlank
        String passportDivisionName,
        @NotBlank
        @Pattern(regexp = "^\\d{6}$")
        String passportDivisionCode,
        @NotNull
        LocalDate passportDateOfIssue)  implements Serializable {

}
        
