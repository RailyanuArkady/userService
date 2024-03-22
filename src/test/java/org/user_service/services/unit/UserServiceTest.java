package org.user_service.services.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.user_service.dto.response.UserResponseDTO;
import org.user_service.mapper.UserMapper;
import org.user_service.model.User;
import org.user_service.repository.UserRepository;
import org.user_service.services.UserService;
import org.user_service.services.util.TestUtils;

import java.util.Optional;

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

        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(TestUtils.MOCKED_ID);
        Mockito.doReturn(expectedUserResponseDto).when(userMapper).userToResponseDTO(user);

        UserResponseDTO actualUser = userService.findUserById(TestUtils.MOCKED_ID);

        Assertions.assertEquals(expectedUserResponseDto, actualUser);
    }

//    @Test
//    void TestSaveUser() {
//        when(userMapper.dtoToUser(userRequestDTO)).thenReturn(user);
//        when(userRepository.save(user)).thenReturn(user);
//
//        UUID externalId = userService.saveUser(userRequestDTO);
//
//        verify(userRepository, times(1)).save(user);
//        assertEquals(user.getExternalId(), externalId);
//    }
//
//
//    @Test
//    void TestUpdateUser() {
//
//        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(user));
//
//        userResponseDTO = userService.updateUser(USER_ID, userRequestDTO);
//
//        verify(userMapper, times(1)).updateUserFromDTO(userRequestDTO, user);
//        assertEquals(PersonSex.MALE, userResponseDTO.sex());
//    }
//
//    @Test
//    void TestDeleteUser() {
//        when(userRepository.findById(USER_ID)).thenReturn(Optional.ofNullable(user));
//        userService.deleteUser(USER_ID);
//        assertAll(
//                () -> assertTrue(user.isDeleted()),
//                () -> assertTrue(Optional.of(user.getPassport()).isPresent())
//        );
//
//    }
}