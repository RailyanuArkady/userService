package com.example.userservice.controller;

import com.example.userservice.dto.request.PassportUpdateRequest;
import com.example.userservice.dto.response.PassportResponse;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.enums.Sex;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.service.PassportService;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PassportController.class)
public class PassportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PassportService passportService;

    @Autowired
    private ObjectMapper objectMapper;

    private final UUID testUserId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

    @Test
    @DisplayName("Return user response when passport update is successful")
    void updatePassport_WithValidData_ReturnsUserResponse() throws Exception {
        PassportUpdateRequest request = new PassportUpdateRequest(
                "4510",
                "123456",
                "ОВД Тверского района г. Москвы",
                "770053",
                LocalDate.of(2020, 5, 15)
        );
        PassportResponse passportResponse = new PassportResponse(
                "4510",
                "123456",
                "ОВД Тверского района г. Москвы",
                "770053",
                LocalDate.of(2020, 5, 15)
        );
        UserResponse expectedResponse = new UserResponse(
                "+79161234567",
                Sex.MALE,
                null,
                LocalDate.of(1990, 1, 15),
                passportResponse
        );
        when(passportService.updatePassport(eq(testUserId), any(PassportUpdateRequest.class)))
                .thenReturn(expectedResponse);
        mockMvc.perform(put("/public/api/v1/users/{userId}/passport", testUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value("+79161234567"))
                .andExpect(jsonPath("$.sex").value("MALE"))
                .andExpect(jsonPath("$.birthdate").value("1990-01-15"))
                .andExpect(jsonPath("$.passport.passportSeries").value("4510"))
                .andExpect(jsonPath("$.passport.passportNumber").value("123456"));
    }

    @Test
    @DisplayName("Return not found when user does not exist")
    void updatePassport_WithNonExistentUser_ReturnsNotFound() throws Exception {
        PassportUpdateRequest request = new PassportUpdateRequest(
                "4510",
                "123456",
                "ОВД Тверского района г. Москвы",
                "770053",
                LocalDate.of(2020, 5, 15)
        );
        UUID nonExistentUserId = UUID.fromString("999e4567-e89b-12d3-a456-426614174000");

        when(passportService.updatePassport(eq(nonExistentUserId), any(PassportUpdateRequest.class)))
                .thenThrow(new UserNotFoundException("User not found with id: " + nonExistentUserId));
        mockMvc.perform(put("/public/api/v1/users/{userId}/passport", nonExistentUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Return bad request when passport data is invalid")
    void updatePassport_WithInvalidPassportData_ReturnsBadRequest() throws Exception {
        PassportUpdateRequest invalidRequest = new PassportUpdateRequest(
                "45A0",
                "12345",
                "ОВД",
                "77053",
                LocalDate.of(2020, 5, 15)
        );

        mockMvc.perform(put("/public/api/v1/users/{userId}/passport", testUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.description", containsString("Invalid passport series")))
                .andExpect(jsonPath("$.description", containsString("Invalid passport number")));
    }

    @Test
    @DisplayName("Return bad request when passport division name is empty")
    void updatePassport_WithEmptyDivisionName_ReturnsBadRequest() throws Exception {
        PassportUpdateRequest invalidRequest = new PassportUpdateRequest(
                "4510",
                "123456",
                "",
                "770053",
                LocalDate.of(2020, 5, 15)
        );
        mockMvc.perform(put("/public/api/v1/users/{userId}/passport", testUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.description", containsString("passportDivisionName")));
    }
}