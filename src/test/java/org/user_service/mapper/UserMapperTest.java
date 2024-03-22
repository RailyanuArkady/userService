package org.user_service.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.user_service.dto.request.UserRequestDTO;
import org.user_service.dto.response.UserResponseDTO;
import org.user_service.model.Passport;
import org.user_service.model.User;
import org.user_service.services.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {
    @Mock
    private PassportMapper passportMapper;
    @InjectMocks
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void TestUserToDTO() {
        User user = TestUtils.buildUser();

        UserRequestDTO userRequestDTO = userMapper.userToDTO(user);

        assertEquals(user.getPhotoId(), userRequestDTO.photoId());
    }

    @Test
    void TestDtoToUser() {
        UserRequestDTO userRequestDTO = TestUtils.buildUserRequestDTO();
        Passport passport = TestUtils.buildPassport();

        doReturn(passport).when(passportMapper).passportRequestDTOToPassport(any());

        User user = userMapper.dtoToUser(userRequestDTO);

        assertEquals(userRequestDTO.phone() , user.getPhone());
    }

    @Test
    void TestUserToResponseDTO() {
        User user = TestUtils.buildUser();

        UserResponseDTO userResponseDTO = userMapper.userToResponseDTO(user);

        assertEquals(user.getSex() , userResponseDTO.sex());
    }

    @Test
    void TestUpdateUserFromDTO() {
        User user = TestUtils.buildUser();
        UserRequestDTO userRequestDTO = TestUtils.buildUserRequestDTO();

        doNothing().when(passportMapper).updatePassportFromDTO(any() , any());

        userMapper.updateUserFromDTO(userRequestDTO, user);

        assertEquals(user.getBirthdate(), userRequestDTO.birthdate());
    }
}