package com.example.userservice.service;

import com.example.userservice.dto.request.PassportUpdateRequest;
import com.example.userservice.dto.response.PassportResponse;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.entities.Passport;
import com.example.userservice.entities.Users;
import com.example.userservice.enums.Sex;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.mapper.PassportMapper;
import com.example.userservice.mapper.UserMapper;
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
class PassportServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PassportMapper passportMapper;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private PassportService passportService;

    @Test
    @DisplayName("Updating passport data of an existing user")
    void updatePassport_ShouldUpdatePassportAndReturnUserResponse() {
        UUID userId = UUID.randomUUID();
        PassportUpdateRequest updateRequest = TestDataFactory.createValidPassportUpdateRequest();
        Users user = new Users();
        user.setExternalId(userId);
        user.setPassports(java.util.List.of(new Passport()));
        UserResponse expectedResponse = TestDataFactory.createUserResponse();
        when(userRepository.findByExternalId(userId)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user, passportMapper)).thenReturn(expectedResponse);
        UserResponse result = passportService.updatePassport(userId, updateRequest);
        assertThat(result).isEqualTo(expectedResponse);
        verify(userRepository).findByExternalId(userId);
        verify(userMapper).updatePassport(updateRequest, user, passportMapper);
        verify(userMapper).toResponse(user, passportMapper);
    }

    @Test
    @DisplayName("Attempt to update a non-existent user's passport")
    void updatePassport_ShouldThrowUserNotFoundException_WhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        PassportUpdateRequest updateRequest = TestDataFactory.createValidPassportUpdateRequest();
        when(userRepository.findByExternalId(userId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> passportService.updatePassport(userId, updateRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found with id: " + userId);

        verify(userRepository).findByExternalId(userId);
        verify(userMapper, never()).updatePassport(any(), any(), any());
        verify(userMapper, never()).toResponse(any(), any());
    }
}