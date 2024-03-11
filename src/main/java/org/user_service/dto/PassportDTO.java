package org.user_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PassportDTO {
    @NotNull
    @Size(min = 4, max = 4)
    private String passportSeries;
    @NotNull
    @Size(min = 6, max = 6)
    private String passportNumber;
    @NotNull
    private String passportDivisionName;
    @NotNull
    @Size(min = 6, max = 6)
    private String passportDivisionCode;
    @NotNull
    private LocalDate passportDateOfIssue;
}
