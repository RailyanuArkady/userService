package com.example.userservice.mapper;

import com.example.userservice.dto.UserCreateRequest;
import com.example.userservice.dto.UserCreateResponse;
import com.example.userservice.entities.Passport;
import com.example.userservice.entities.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = PassportMapper.class)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "photoUrl", source = "photoId")
    Users toEntity(UserCreateRequest request);
    UserCreateResponse toResponse(Users user);

    default Users toResponsePass(UserCreateRequest request, PassportMapper passportMapper) {
        Users user = toEntity(request);
        Passport passport = passportMapper.toEntity(request.passport());
        passport.setUser(user);
        user.setPassports(List.of(passport));
        return user;
    }
}