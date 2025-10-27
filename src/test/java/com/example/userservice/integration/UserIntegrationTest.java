package com.example.userservice.integration;

import com.example.userservice.dto.request.UserCreateRequest;
import com.example.userservice.dto.request.UserUpdateRequest;
import com.example.userservice.dto.response.UserCreateResponse;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.enums.Sex;
import com.example.userservice.utils.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserIntegrationTest extends BaseIntegrationTest {

    @Test
    @DisplayName("POST /users → creates a user with a passport")
    void createUser_ShouldSaveUserWithPassport() {
        UserCreateRequest request = TestDataFactory.createValidUserRequest();

        ResponseEntity<UserCreateResponse> response = restTemplate.postForEntity(
                getBaseUrl(),
                new HttpEntity<>(request, createHeaders()),
                UserCreateResponse.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().externalId()).isNotNull();
        ResponseEntity<UserResponse> getResponse = restTemplate.getForEntity(
                getBaseUrl() + "/" + response.getBody().externalId(),
                UserResponse.class
        );
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().phone()).isEqualTo("+79161234567");
        assertThat(getResponse.getBody().passport().passportSeries()).isEqualTo("4510");
    }

    @Test
    @DisplayName("GET /users/{id} → returns the user with a passport")
    void getUser_ShouldReturnUserWithPassport() {
        UserCreateRequest request = TestDataFactory.createValidUserRequest();
        ResponseEntity<UserCreateResponse> createResponse = restTemplate.postForEntity(
                getBaseUrl(),
                new HttpEntity<>(request, createHeaders()),
                UserCreateResponse.class
        );
        UUID userId = createResponse.getBody().externalId();

        ResponseEntity<UserResponse> response = restTemplate.getForEntity(
                getBaseUrl() + "/" + userId,
                UserResponse.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().phone()).isEqualTo("+79161234567");
        assertThat(response.getBody().passport()).isNotNull();
        assertThat(response.getBody().passport().passportSeries()).isEqualTo("4510");
        assertThat(response.getBody().passport().passportNumber()).isEqualTo("123456");
    }

    @Test
    @DisplayName("PUT /users/{id} → updates user data")
    void updateUser_ShouldUpdateUserData() {
        UserCreateRequest request = TestDataFactory.createValidUserRequest();

        ResponseEntity<UserCreateResponse> createResponse = restTemplate.postForEntity(
                getBaseUrl(),
                new HttpEntity<>(request, createHeaders()),
                UserCreateResponse.class
        );
        UUID userId = createResponse.getBody().externalId();

        UserUpdateRequest updateRequest = TestDataFactory.createValidUserUpdateRequest();

        ResponseEntity<UserResponse> updateResponse = restTemplate.exchange(
                getBaseUrl() + "/" + userId,
                HttpMethod.PUT,
                new HttpEntity<>(updateRequest, createHeaders()),
                UserResponse.class
        );
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().phone()).isEqualTo("+79161111111");
        assertThat(updateResponse.getBody().sex()).isEqualTo(Sex.MALE);
        ResponseEntity<UserResponse> getResponse = restTemplate.getForEntity(
                getBaseUrl() + "/" + userId,
                UserResponse.class
        );
        assertThat(getResponse.getBody().phone()).isEqualTo("+79161111111");
    }

    @Test
    @DisplayName("DELETE /users/{id} → marks the user as deleted")
    void deleteUser_ShouldMarkAsDeleted() {
        UserCreateRequest request = TestDataFactory.createValidUserRequest();

        ResponseEntity<UserCreateResponse> createResponse = restTemplate.postForEntity(
                getBaseUrl(),
                new HttpEntity<>(request, createHeaders()),
                UserCreateResponse.class
        );
        UUID userId = createResponse.getBody().externalId();

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                getBaseUrl() + "/" + userId,
                HttpMethod.DELETE,
                new HttpEntity<>(createHeaders()),
                Void.class
        );

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<UserResponse> getResponse = restTemplate.getForEntity(
                getBaseUrl() + "/" + userId,
                UserResponse.class
        );
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}