package com.example.userservice.mapper;

import com.example.userservice.dto.UserCreateRequest;
import com.example.userservice.dto.UserCreateResponse;
import com.example.userservice.entities.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//дополнить маппер, полями isDeleted и externalId должны прямо тут выставлять, проверить мапиться ли паспорт, скорее всего нет для этого над классом надо еще добавить uses = {}
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "passports", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "photoUrl", source = "photoId")
    Users toEntity(UserCreateRequest request);

    //не используется
    UserCreateResponse toResponse(Users user);
}