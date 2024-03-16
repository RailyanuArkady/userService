package org.user_service.dto.response;

import org.user_service.model.PersonSex;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponseDTO(
        String phone,
        PersonSex sex,
        UUID photoId,
        LocalDate birthdate,
        PassportResponseDTO passport) {
}
