package org.user_service.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.user_service.model.PersonSex;

import java.time.LocalDate;
import java.util.UUID;


public record UserRequestDTO(
        String phone,
        @NotNull
        PersonSex sex,

        UUID photoId,
        @NotNull
        LocalDate birthdate,
        @NotNull
        @Valid
        PassportRequestDTO passport) {

    public UserRequestDTO {
        if (phone.isBlank()) {
            throw new IllegalArgumentException("Неверный формат номера телефона");
        }
        phone = phone.replaceAll("\\D", "");
        String plusSeven = "+7";
        if (phone.length() == 11) {
            phone = plusSeven + phone.substring(1);
        } else if (phone.length() == 10) {
            phone = plusSeven + phone;
        } else {
            throw new IllegalArgumentException("Неверный формат номера телефона");
        }

    }

}
