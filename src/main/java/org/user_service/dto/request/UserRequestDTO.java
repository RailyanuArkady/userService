package org.user_service.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.user_service.model.PersonSex;

import java.time.LocalDate;
import java.util.UUID;


public record UserRequestDTO(
        @NotBlank
        @Size(min = 10, max = 18)
        String phone,
        @NotNull
        PersonSex sex,
        UUID photoId,
        @NotNull
        LocalDate birthdate,
        @NotNull
        PassportRequestDTO passport) {

}
