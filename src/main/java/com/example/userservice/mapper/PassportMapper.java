package com.example.userservice.mapper;

import com.example.userservice.dto.request.PassportCreateRequest;
import com.example.userservice.dto.request.PassportUpdateRequest;
import com.example.userservice.dto.response.PassportResponse;
import com.example.userservice.entities.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PassportMapper {
    @Mapping(target = "passportSeries", source = "passport.passportSeries")
    @Mapping(target = "passportNumber", source = "passport.passportNumber")
    @Mapping(target = "passportDivisionName", source = "passport.passportDivisionName")
    @Mapping(target = "passportDivisionCode", source = "passport.passportDivisionCode")
    @Mapping(target = "passportDateOfIssue", source = "passport.passportDateOfIssue")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    Passport toEntity(PassportCreateRequest request);

    @Mapping(target = "passportSeries", source = "passport.passportSeries")
    @Mapping(target = "passportNumber", source = "passport.passportNumber")
    @Mapping(target = "passportDivisionName", source = "passport.passportDivisionName")
    @Mapping(target = "passportDivisionCode", source = "passport.passportDivisionCode")
    @Mapping(target = "passportDateOfIssue", source = "passport.passportDateOfIssue")
    void updateRequest(PassportUpdateRequest request, @MappingTarget Passport passport);


    PassportResponse toResponse(Passport passport);
}
