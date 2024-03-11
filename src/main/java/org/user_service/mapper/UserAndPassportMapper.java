package org.user_service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.user_service.dto.PassportDTO;
import org.user_service.dto.UserDTO;
import org.user_service.model.Passport;
import org.user_service.model.User;

@Mapper
public interface UserAndPassportMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "sex", target = "sex")
    @Mapping(source = "photoId", target = "photoId")
    @Mapping(source = "birthdate", target = "birthdate")
    UserDTO userToDto(User user);
    @InheritInverseConfiguration
    User dtoToUser(UserDTO userDTO);
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source = "passportSeries", target = "passportSeries")
    @Mapping(source = "passportNumber", target = "passportNumber")
    @Mapping(source = "passportDivisionName", target = "passportDivisionName")
    @Mapping(source = "passportDivisionCode", target = "passportDivisionCode")
    @Mapping(source = "passportDateOfIssue", target = "passportDateOfIssue")
    PassportDTO passportToPassportDTO(Passport passport);
    @InheritInverseConfiguration
    Passport passportDTOToPassport(PassportDTO passportDTO);
}
