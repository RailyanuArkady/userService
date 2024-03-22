package org.user_service.services.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.user_service.dto.request.UserRequestDTO;
import org.user_service.dto.response.UserResponseDTO;
import org.user_service.mapper.UserMapper;
import org.user_service.model.PersonSex;
import org.user_service.model.User;
import org.user_service.repository.UserRepository;
import org.user_service.services.UserService;
import org.user_service.services.util.TestUtils;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.user_service.services.util.TestUtils.MOCKED_ID;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    @Test
    void findUserByIdWhenOk() {
        User user = TestUtils.buildUser();
        UserResponseDTO expectedUserResponseDto = TestUtils.buildUserResponseDTO();

        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(MOCKED_ID);
        Mockito.doReturn(expectedUserResponseDto).when(userMapper).userToResponseDTO(user);

        UserResponseDTO actualUser = userService.findUserById(MOCKED_ID);

        assertEquals(expectedUserResponseDto, actualUser);
    }

    @Test
    void TestSaveUser() {
        User user = TestUtils.buildUser();
        UserRequestDTO userRequestDTO = TestUtils.buildUserRequestDTO();

        doReturn(user).when(userMapper).dtoToUser(userRequestDTO);
        doReturn(user).when(userRepository).save(user);

        UUID externalId = userService.saveUser(userRequestDTO);

        assertEquals(user.getExternalId(), externalId);
    }

    @Test
    void TestUpdateUser() {
        User user = TestUtils.buildUser();
        UserRequestDTO userRequestDTO = TestUtils.buildUserRequestDTO();
        UserResponseDTO userResponseDTO = TestUtils.buildUserResponseDTO();

        doReturn(Optional.of(user)).when(userRepository).findById(MOCKED_ID);
        doNothing().when(userMapper).updateUserFromDTO(userRequestDTO, user);
        doReturn(userResponseDTO).when(userMapper).userToResponseDTO(user);

        UserResponseDTO userResponseDTOActual = userService.updateUser(MOCKED_ID, userRequestDTO);

        assertEquals(PersonSex.MALE, userResponseDTOActual.sex());
    }

    @Test
    void TestDeleteUser() {
        User user = TestUtils.buildUser();

        doReturn(Optional.ofNullable(user)).when(userRepository).findById(MOCKED_ID);
        userService.deleteUser(MOCKED_ID);

        assertAll(
                () -> assertTrue(user.isDeleted()),
                () -> assertTrue(Optional.of(user.getPassport()).isPresent())
        );

    }
}