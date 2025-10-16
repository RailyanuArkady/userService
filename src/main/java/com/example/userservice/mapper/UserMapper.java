package com.example.userservice.mapper;

import com.example.userservice.dto.request.PassportUpdateRequest;
import com.example.userservice.dto.request.UserCreateRequest;
import com.example.userservice.dto.request.UserUpdateRequest;
import com.example.userservice.dto.response.PassportResponse;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.entities.Passport;
import com.example.userservice.entities.Users;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = PassportMapper.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "photoUrl", source = "photoId")
    Users toEntity(UserCreateRequest request);

    @Mapping(target = "photoId", source = "photoUrl")
    @Mapping(target = "passport", source = "passports")
    UserResponse toResponse(Users user, @Context PassportMapper passportMapper);

    void updateUser(UserUpdateRequest request, @MappingTarget Users user);

    default Users toResponsePass(UserCreateRequest request, PassportMapper passportMapper) {
        Users user = toEntity(request);
        Passport passport = passportMapper.toEntity(request.passport());
        passport.setUser(user);
        user.setPassports(List.of(passport));
        return user;
    }

    default PassportResponse mapPassports(List<Passport> passports, @Context PassportMapper passportMapper) {
        return passportMapper.toResponse(passports.get(0));
    }

    default void updatePassport(PassportUpdateRequest request, Users user, PassportMapper passportMapper) {
        Passport passport = user.getPassports().get(0);
        passportMapper.updateRequest(request, passport);
    }
}
