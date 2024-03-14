package org.user_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.user_service.dto.request.UserRequestDTO;
import org.user_service.model.User;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class, uses = PassportMapper.class)
public interface UserMapper {


    @Mapping(target = "externalId", expression = "java(UUID.randomUUID())")
    @Mapping(target = "phone", qualifiedByName = "phoneNumberFormatter")
    User dtoToUser(UserRequestDTO userRequestDTO);


    @Named("phoneNumberFormatter")
    static String phoneNumberFormatter(String phone) {
        String phoneNormal = phone.replaceAll("\\D", "");
        String plusSeven = "+7";
        if (phoneNormal.length() == 11) {
            return plusSeven + phoneNormal.substring(1);
        } else if (phoneNormal.length() == 10) {
            return plusSeven + phoneNormal;
        }
        throw new IllegalArgumentException("Invalid phone number: " + phone);
    }


}
