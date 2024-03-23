package org.user_service.services.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.user_service.dto.request.UserRequestDTO;
import org.user_service.dto.response.UserResponseDTO;
import org.user_service.mapper.PassportMapper;
import org.user_service.mapper.UserMapper;
import org.user_service.model.User;
import org.user_service.services.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertAll;
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
        UserRequestDTO userRequestDTOExpected = TestUtils.buildUserRequestDTO();

        doReturn(userRequestDTOExpected.passport()).when(passportMapper).passportToPassportRequestDTO(user.getPassport());

        UserRequestDTO userRequestDTOActual = userMapper.userToDTO(user);

        assertEquals(userRequestDTOExpected, userRequestDTOActual);
    }

    @Test
    void TestDtoToUser() {
        User userExpected = TestUtils.buildUser();
        UserRequestDTO userRequestDTO = TestUtils.buildUserRequestDTO();

        doReturn(userExpected.getPassport()).when(passportMapper).passportRequestDTOToPassport(userRequestDTO.passport());

        User userActual = userMapper.dtoToUser(userRequestDTO);

        assertEquals(userExpected.getPassport(), userActual.getPassport());

    }

    @Test
    void TestUserToResponseDTO() {
        User user = TestUtils.buildUser();
        UserResponseDTO userResponseDTOExpected = TestUtils.buildUserResponseDTO();

        UserResponseDTO userResponseDTOActual = userMapper.userToResponseDTO(user);

        assertEquals(userResponseDTOExpected, userResponseDTOActual);
    }

    @Test
    void TestUpdateUserFromDTO() {
        User user = TestUtils.buildUser();
        UserRequestDTO userRequestDTOExpected = TestUtils.buildUserRequestDTO();

        doNothing().when(passportMapper).updatePassportFromDTO(any(), any());

        userMapper.updateUserFromDTO(userRequestDTOExpected, user);

        assertAll(
                ()->assertEquals(user.getBirthdate(), userRequestDTOExpected.birthdate()),
                ()->assertEquals(user.getSex(), userRequestDTOExpected.sex()),
                ()->assertEquals(user.getPhotoId(), userRequestDTOExpected.photoId()),
                ()->assertEquals(user.getPhone(), userRequestDTOExpected.phone()));
    }
}