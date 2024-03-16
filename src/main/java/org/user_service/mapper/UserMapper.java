package org.user_service.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.user_service.dto.request.UserRequestDTO;
import org.user_service.dto.response.GetUserResponseDTO;
import org.user_service.model.User;

import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class, uses = PassportMapper.class, builder = @Builder(disableBuilder = true))
public interface UserMapper {

    UserRequestDTO userToDTO(User user);

    @Mapping(target = "externalId", expression = "java(UUID.randomUUID())")
    User dtoToUser(UserRequestDTO userRequestDTO);

    GetUserResponseDTO userToResponseDTO(User user);

}
