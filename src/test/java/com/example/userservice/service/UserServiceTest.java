package com.example.userservice.service;

import com.example.userservice.dto.projection.UserProjection;
import com.example.userservice.dto.request.PassportCreateRequest;
import com.example.userservice.dto.request.UserCreateRequest;
import com.example.userservice.dto.request.UserUpdateRequest;
import com.example.userservice.dto.response.PassportResponse;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.entities.Users;
import com.example.userservice.enums.Sex;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.mapper.PassportMapper;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.PassportRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.utils.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PassportMapper passportMapper;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Create user with valid data and save to repository")
    void createUser_ShouldCreateAndSaveUser() {
        UserCreateRequest createRequest = TestDataFactory.createValidUserRequest();
        Users user = new Users();
        user.setExternalId(UUID.randomUUID());
        Users savedUser = new Users();
        UUID expectedExternalId = UUID.randomUUID();
        savedUser.setExternalId(expectedExternalId);
        when(userMapper.toResponsePass(createRequest, passportMapper)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        UUID result = userService.createUser(createRequest);
        assertThat(result).isEqualTo(expectedExternalId);
        verify(userMapper).toResponsePass(createRequest, passportMapper);
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Return user projection when user exists")
    void getUser_ShouldReturnUserProjection() {
        UUID userId = UUID.randomUUID();
        UserProjection expectedProjection = mock(UserProjection.class);
        when(userRepository.findProjectionId(userId)).thenReturn(Optional.of(expectedProjection));
        UserProjection result = userService.getUser(userId);
        assertThat(result).isEqualTo(expectedProjection);
        verify(userRepository).findProjectionId(userId);
    }

    @Test
    @DisplayName("Throw UserNotFoundException when getting non-existent user")
    void getUser_ShouldThrowUserNotFoundException_WhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findProjectionId(userId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.getUser(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found with id: " + userId);
        verify(userRepository).findProjectionId(userId);
    }

    @Test
    @DisplayName("Update user data and return updated response")
    void updateUser_ShouldUpdateAndReturnUserResponse() {
        UUID userId = UUID.randomUUID();
        UserUpdateRequest updateRequest = TestDataFactory.createInvalidUserUpdateRequest();
        Users user = new Users();
        user.setExternalId(userId);
        UserResponse expectedResponse = TestDataFactory.createUserResponse();
        when(userRepository.findByExternalId(userId)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user, passportMapper)).thenReturn(expectedResponse);
        UserResponse result = userService.updateUser(userId, updateRequest);
        assertThat(result).isEqualTo(expectedResponse);
        verify(userRepository).findByExternalId(userId);
        verify(userMapper).updateUser(updateRequest, user);
        verify(userMapper).toResponse(user, passportMapper);
    }

    @Test
    @DisplayName("Throw UserNotFoundException when updating non-existent user")
    void updateUser_ShouldThrowUserNotFoundException_WhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        UserUpdateRequest updateRequest = TestDataFactory.createInvalidUserUpdateRequest();
        when(userRepository.findByExternalId(userId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.updateUser(userId, updateRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found with id: " + userId);
        verify(userRepository).findByExternalId(userId);
        verify(userMapper, never()).updateUser(any(), any());
        verify(userMapper, never()).toResponse(any(), any());
    }

    @Test
    @DisplayName("Mark user as deleted when soft deletion")
    void deleteUser_ShouldMarkUserAsDeleted() {
        UUID userId = UUID.randomUUID();
        Users user = new Users();
        user.setExternalId(userId);
        user.setDeleted(false);
        when(userRepository.findByExternalId(userId)).thenReturn(Optional.of(user));
        userService.deleteUser(userId);
        assertThat(user.isDeleted()).isTrue();
        verify(userRepository).findByExternalId(userId);
    }

    @Test
    @DisplayName("Throw UserNotFoundException when deleting non-existent user")
    void deleteUser_ShouldThrowUserNotFoundException_WhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findByExternalId(userId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.deleteUser(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found with id: " + userId);

        verify(userRepository).findByExternalId(userId);
    }

    @Test
    @DisplayName("Return saved user externalId after creation")
    void createUser_ShouldReturnSavedUserExternalId() {
        UserCreateRequest createRequest = TestDataFactory.createValidUserRequest();
        Users user = new Users();
        Users savedUser = new Users();
        UUID expectedExternalId = UUID.randomUUID();
        savedUser.setExternalId(expectedExternalId);
        when(userMapper.toResponsePass(createRequest, passportMapper)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        UUID result = userService.createUser(createRequest);
        assertThat(result).isEqualTo(expectedExternalId);
        verify(userRepository).save(user);
    }
}