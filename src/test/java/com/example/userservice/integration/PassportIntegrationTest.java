package com.example.userservice.integration;

import com.example.userservice.dto.request.PassportUpdateRequest;
import com.example.userservice.dto.request.UserCreateRequest;
import com.example.userservice.dto.response.UserCreateResponse;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.utils.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PassportIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("PUT /users/{id} → updates user data")
    void updatePassport_ShouldUpdateUserPassport() {
        UserCreateRequest createRequest = TestDataFactory.createValidUserRequest();

        ResponseEntity<UserCreateResponse> createResponse = restTemplate.postForEntity(
                getBaseUrl(),
                new HttpEntity<>(createRequest, createHeaders()),
                UserCreateResponse.class
        );
        UUID userId = createResponse.getBody().externalId();
        PassportUpdateRequest passportUpdateRequest = TestDataFactory.createValidPassportUpdateRequest();

        ResponseEntity<UserResponse> response = restTemplate.exchange(
                getBaseUrl() + "/" + userId + "/passport",
                HttpMethod.PUT,
                new HttpEntity<>(passportUpdateRequest, createHeaders()),
                UserResponse.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().passport().passportSeries()).isEqualTo("4511");
        assertThat(response.getBody().passport().passportNumber()).isEqualTo("654321");
        assertThat(response.getBody().passport().passportDivisionName()).isEqualTo("Новое подразделение");
        assertThat(response.getBody().passport().passportDivisionCode()).isEqualTo("770054");
        ResponseEntity<UserResponse> getResponse = restTemplate.getForEntity(
                getBaseUrl() + "/" + userId,
                UserResponse.class
        );
        assertThat(getResponse.getBody().passport().passportSeries()).isEqualTo("4511");
    }
}