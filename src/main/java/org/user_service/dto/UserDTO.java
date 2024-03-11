package org.user_service.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.user_service.model.PersonSex;

import java.time.LocalDate;

@Setter
@Getter
public class UserDTO {
    @Size(min = 10, max = 11)
    private String phone;
    @NotNull
    private PersonSex sex;
    @NotNull
    private String photoId;
    @NotNull
    private LocalDate birthdate;
    @NotNull
    private PassportDTO passport;
}
