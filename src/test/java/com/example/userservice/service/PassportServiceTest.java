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
        PassportUpdateRequest updateRequest = new PassportUpdateRequest(
                "4511", "654321", "Новое подразделение", "770054", LocalDate.of(2021, 6, 20)
        );
        Users user = new Users();
        user.setExternalId(userId);
        user.setPassports(java.util.List.of(new Passport()));
        UserResponse expectedResponse = new UserResponse(
                "+79161234567", Sex.MALE, UUID.randomUUID(), LocalDate.of(1990, 1, 1),
                new PassportResponse("4511", "654321", "Новое подразделение", "770054", LocalDate.of(2021, 6, 20))
        );
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
        PassportUpdateRequest updateRequest = new PassportUpdateRequest(
                "4511", "654321", "Новое подразделение", "770054", LocalDate.of(2021, 6, 20)
        );
        when(userRepository.findByExternalId(userId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> passportService.updatePassport(userId, updateRequest))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found with id: " + userId);

        verify(userRepository).findByExternalId(userId);
        verify(userMapper, never()).updatePassport(any(), any(), any());
        verify(userMapper, never()).toResponse(any(), any());
    }
}