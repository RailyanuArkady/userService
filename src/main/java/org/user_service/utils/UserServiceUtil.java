package org.user_service.utils;

import jakarta.validation.constraints.NotBlank;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserServiceUtil {
    private final String PLUS_SEVEN = "+7";
    public String phoneNumberFormatter(@NotBlank String phone){
        phone = phone.replaceAll("\\D", "");
        if (phone.length() == 11) {
            return PLUS_SEVEN + phone.substring(1);
        } else if (phone.length() == 10) {
            return PLUS_SEVEN + phone;
        } else {
            throw new IllegalArgumentException("Неверный формат номера телефона");
        }
    }
}
