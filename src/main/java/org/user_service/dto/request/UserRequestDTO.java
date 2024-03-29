package org.user_service.dto.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.user_service.model.PersonSex;
import org.user_service.utils.UserServiceUtil;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;


public record UserRequestDTO(
        @NotBlank
        String phone,
        @NotNull
        PersonSex sex,
        UUID photoId,
        @NotNull
        LocalDate birthdate,
        @NotNull
        @Valid
        PassportRequestDTO passport) implements Serializable {

    public UserRequestDTO {
        phone = UserServiceUtil.phoneNumberFormatter(phone);
    }

}
