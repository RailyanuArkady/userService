package com.example.userservice.controller;

import com.example.userservice.dto.projection.PassportProjection;
import com.example.userservice.dto.projection.UserProjection;
import com.example.userservice.dto.request.PassportCreateRequest;
import com.example.userservice.dto.request.UserCreateRequest;
import com.example.userservice.dto.request.UserUpdateRequest;
import com.example.userservice.dto.response.PassportResponse;
import com.example.userservice.dto.response.UserCreateResponse;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.enums.Sex;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID testUserId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    private final UUID testPhotoId = UUID.fromString("223e4567-e89b-12d3-a456-426614174000");

    @Test
    @DisplayName("Return created status when user is successfully created")
    void createUser_WithValidData_ReturnsCreated() throws Exception {
        PassportCreateRequest passportRequest = new PassportCreateRequest(
                "4510",
                "123456",
                "ОВД Тверского района г. Москвы",
                "770-053",
                LocalDate.of(2020, 5, 15)
        );
        UserCreateRequest request = new UserCreateRequest(
                "+79161234567",
                Sex.MALE,
                testPhotoId,
                LocalDate.of(1990, 1, 15),
                "ivan.ivanov@example.com",
                passportRequest
        );
        UUID createdUserId = UUID.fromString("423e4567-e89b-12d3-a456-426614174000");
        UserCreateResponse expectedResponse = new UserCreateResponse(createdUserId);

        when(userService.createUser(any(UserCreateRequest.class)))
                .thenReturn(createdUserId);
        mockMvc.perform(post("/public/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.externalId").value(createdUserId.toString()));
    }

    @Test
    @DisplayName("Return user projection with passport data")
    void getUser_WithExistingId_ReturnsUserProjectionWithPassport() throws Exception {
        PassportProjection passportProjection = new PassportProjection() {
            @Override
            public String getPassportSeries() {
                return "4510";
            }

            @Override
            public String getPassportNumber() {
                return "123456";
            }

            @Override
            public String getPassportDivisionName() {
                return "ОВД Тверского района г. Москвы";
            }

            @Override
            public String getPassportDivisionCode() {
                return "770-053";
            }

            @Override
            public LocalDate getPassportDateOfIssue() {
                return LocalDate.of(2020, 5, 15);
            }
        };
        UserProjection userProjection = new UserProjection() {
            @Override
            public String getPhone() {
                return "+79161234567";
            }

            @Override
            public Sex getSex() {
                return Sex.MALE;
            }

            @Override
            public UUID getPhotoId() {
                return testPhotoId;
            }

            @Override
            public LocalDate getBirthdate() {
                return LocalDate.of(1990, 1, 15);
            }

            @Override
            public String getEmail() {
                return "ivan@example.com";
            }

            @Override
            public PassportProjection getPassport() {
                return passportProjection;
            }
        };
        when(userService.getUser(eq(testUserId)))
                .thenReturn(userProjection);
        mockMvc.perform(get("/public/api/v1/users/{externalId}", testUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value("+79161234567"))
                .andExpect(jsonPath("$.sex").value("MALE"))
                .andExpect(jsonPath("$.photoId").value(testPhotoId.toString()))
                .andExpect(jsonPath("$.birthdate").value("1990-01-15"))
                .andExpect(jsonPath("$.email").value("ivan@example.com"))
                .andExpect(jsonPath("$.passport.passportSeries").value("4510"))
                .andExpect(jsonPath("$.passport.passportNumber").value("123456"))
                .andExpect(jsonPath("$.passport.passportDivisionName").value("ОВД Тверского района г. Москвы"))
                .andExpect(jsonPath("$.passport.passportDivisionCode").value("770-053"))
                .andExpect(jsonPath("$.passport.passportDateOfIssue").value("2020-05-15"));
    }

    @Test
    @DisplayName("Return not found when getting non-existent user")
    void getUser_WithNonExistentId_ReturnsNotFound() throws Exception {
        UUID nonExistentId = UUID.fromString("999e4567-e89b-12d3-a456-426614174000");

        when(userService.getUser(eq(nonExistentId)))
                .thenThrow(new UserNotFoundException("User not found"));
        mockMvc.perform(get("/public/api/v1/users/{externalId}", nonExistentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Return user response with passport when update is successfu")
    void updateUser_WithValidData_ReturnsUserResponseWithPassport() throws Exception {
        UserUpdateRequest request = new UserUpdateRequest(
                "+79161234567",
                Sex.FEMALE,
                testPhotoId,
                LocalDate.of(1995, 6, 20)
        );
        PassportResponse passportResponse = new PassportResponse(
                "4510",
                "123456",
                "ОВД Тверского района г. Москвы",
                "770-053",
                LocalDate.of(2020, 5, 15)
        );
        UserResponse expectedResponse = new UserResponse(
                "+79161234567",
                Sex.FEMALE,
                testPhotoId,
                LocalDate.of(1995, 6, 20),
                passportResponse
        );
        when(userService.updateUser(eq(testUserId), any(UserUpdateRequest.class)))
                .thenReturn(expectedResponse);
        mockMvc.perform(put("/public/api/v1/users/{externalId}", testUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value("+79161234567"))
                .andExpect(jsonPath("$.sex").value("FEMALE"))
                .andExpect(jsonPath("$.photoId").value(testPhotoId.toString()))
                .andExpect(jsonPath("$.birthdate").value("1995-06-20"))
                .andExpect(jsonPath("$.passport.passportSeries").value("4510")) // или passportSeries в зависимости от структуры
                .andExpect(jsonPath("$.passport.passportNumber").value("123456")); // или passportNumber
    }

    @Test
    @DisplayName("Return bad request when user update data is invalid")
    void updateUser_WithInvalidData_ReturnsBadRequest() throws Exception {
        UserUpdateRequest invalidRequest = new UserUpdateRequest(
                "invalid-phone",
                Sex.MALE,
                testPhotoId,
                LocalDate.of(1990, 1, 15)
        );
        mockMvc.perform(put("/public/api/v1/users/{externalId}", testUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Return no content when user is successfully deleted")
    void deleteUser_WithExistingId_ReturnsNoContent() throws Exception {
        doNothing().when(userService).deleteUser(eq(testUserId));
        mockMvc.perform(delete("/public/api/v1/users/{externalId}", testUserId))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(testUserId);
    }

    @Test
    @DisplayName("Return not found when deleting non-existent user")
    void deleteUser_WithNonExistentId_ReturnsNotFound() throws Exception {
        UUID nonExistentId = UUID.fromString("999e4567-e89b-12d3-a456-426614174000");

        doThrow(new UserNotFoundException("User not found"))
                .when(userService).deleteUser(eq(nonExistentId));
        mockMvc.perform(delete("/public/api/v1/users/{externalId}", nonExistentId))
                .andExpect(status().isNotFound());
    }

}