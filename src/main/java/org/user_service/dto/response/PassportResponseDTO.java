package org.user_service.dto.response;

import java.io.Serializable;
import java.time.LocalDate;

public record PassportResponseDTO(
        String passportSeries,
        String passportNumber,
        String passportDivisionName,
        String passportDivisionCode,
        LocalDate passportDateOfIssue)  implements Serializable {
}
