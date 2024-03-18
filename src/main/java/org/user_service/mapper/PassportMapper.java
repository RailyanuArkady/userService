package org.user_service.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.user_service.dto.request.PassportRequestDTO;
import org.user_service.model.Passport;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class, builder = @Builder(disableBuilder = true))

public interface PassportMapper {

    PassportRequestDTO passportToPassportRequestDTO(Passport passport);

    @Mapping(target = "externalId", expression = "java(UUID.randomUUID())")
    Passport passportRequestDTOToPassport(PassportRequestDTO passportRequestDTO);

    void updatePassportFromDTO(PassportRequestDTO passportRequestDTO, @MappingTarget Passport passport);

}
