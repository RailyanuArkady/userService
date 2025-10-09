package com.example.userservice.mapper;

import com.example.userservice.dto.PassportCreateRequest;
import com.example.userservice.dto.PassportResponse;
import com.example.userservice.dto.PassportUpdateRequest;
import com.example.userservice.entities.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PassportMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Passport toEntity(PassportCreateRequest request);

    void updateRequest(PassportUpdateRequest request, @MappingTarget Passport passport);

    PassportResponse toResponse(Passport passport);
}
