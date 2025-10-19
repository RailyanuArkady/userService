package com.example.userservice.utils;

import com.example.userservice.dto.projection.PassportProjection;
import com.example.userservice.dto.projection.UserProjection;
import com.example.userservice.dto.request.PassportCreateRequest;
import com.example.userservice.dto.request.PassportUpdateRequest;
import com.example.userservice.dto.request.UserCreateRequest;
import com.example.userservice.dto.request.UserUpdateRequest;
import com.example.userservice.dto.response.PassportResponse;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.entities.Passport;
import com.example.userservice.entities.Users;
import com.example.userservice.enums.Sex;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class TestDataFactory {

    public static PassportCreateRequest createValidPassportRequest() {
        return new PassportCreateRequest(
                "4510", "123456", "ОВД района", "770053", LocalDate.of(2020, 5, 15)
        );
    }
    public static UserUpdateRequest createInvalidUserUpdateRequest() {
        return new UserUpdateRequest(
                "invalid-phone",
                null,
                null,
                null
        );
    }

    public static PassportUpdateRequest createValidPassportUpdateRequest() {
        return new PassportUpdateRequest(
                "4511", "654321", "Новое подразделение", "770054", LocalDate.of(2021, 6, 20)
        );
    }
    public static Passport createPassportEntity() {
        return Passport.builder()
                .passportSeries("4510")
                .passportNumber("123456")
                .passportDivisionName("ОВД района")
                .passportDivisionCode("770053")
                .passportDateOfIssue(LocalDate.of(2020, 5, 15))
                .externalId(UUID.randomUUID())
                .build();
    }
    public static UserCreateRequest createValidUserRequest() {
        return new UserCreateRequest(
                "+79161234567", Sex.MALE, UUID.randomUUID(), LocalDate.of(1990, 1, 1),
                "ivan@mail.ru", createValidPassportRequest()
        );
    }
    public static UserUpdateRequest createValidUserUpdateRequest() {
        return new UserUpdateRequest(
                "+79161111111", Sex.MALE, UUID.randomUUID(), LocalDate.of(1990, 1, 1)
        );
    }
    public static Users createUserEntity() {
        Users user = Users.builder()
                .externalId(UUID.randomUUID())
                .phone("+79161234567")
                .sex(Sex.MALE)
                .photoUrl(UUID.randomUUID())
                .birthdate(LocalDate.of(1990, 1, 1))
                .email("ivan@mail.ru")
                .isDeleted(false)
                .build();

        Passport passport = createPassportEntity();
        passport.setUser(user);
        user.setPassports(List.of(passport));

        return user;
    }
    public static PassportCreateRequest createInvalidPassportRequest() {
        return new PassportCreateRequest(
                "45A0", "12345", "", "77053", LocalDate.of(2020, 5, 15)
        );
    }
    public static PassportResponse createPassportResponse() {
        return new PassportResponse(
                "4510", "123456", "ОВД района", "770053", LocalDate.of(2020, 5, 15)
        );
    }
    public static UserResponse createUserResponse() {
        return new UserResponse(
                "+79161234567",
                Sex.MALE,
                UUID.fromString("123e4567-e89b-12d3-a456-426614174000"),
                LocalDate.of(1990, 1, 1),
                createPassportResponse()
        );
    }
    public static UserProjection createUserProjection() {
        UserProjection projection = mock(UserProjection.class);

        when(projection.getPhone()).thenReturn("+79161234567");
        when(projection.getSex()).thenReturn(Sex.MALE);
        when(projection.getPhotoId()).thenReturn(UUID.randomUUID());
        when(projection.getBirthdate()).thenReturn(LocalDate.of(1990, 1, 1));
        when(projection.getEmail()).thenReturn("ivan@mail.ru");
        when(projection.getPassport()).thenReturn(createPassportProjection());

        return projection;
    }
    public static PassportProjection createPassportProjection() {
        PassportProjection projection = mock(PassportProjection.class);

        when(projection.getPassportSeries()).thenReturn("4510");
        when(projection.getPassportNumber()).thenReturn("123456");
        when(projection.getPassportDivisionName()).thenReturn("ОВД района");
        when(projection.getPassportDivisionCode()).thenReturn("770053");
        when(projection.getPassportDateOfIssue()).thenReturn(LocalDate.of(2020, 5, 15));

        return projection;
    }
    public static PassportUpdateRequest createInvalidPassportUpdate() {
        return new PassportUpdateRequest(
                "45A0",
                "12345",
                "ОВД",
                "77053",
                LocalDate.of(2020, 5, 15)
        );
    }
    public static PassportUpdateRequest createPassportRequestWithEmptyDivision() {
        return new PassportUpdateRequest(
                "4510",
                "123456",
                "",
                "770053",
                LocalDate.of(2020, 5, 15)
        );
    }
    public static PassportUpdateRequest createPassportRequestWithNullDate() {
        return new PassportUpdateRequest(
                "4510",
                "123456",
                "ОВД",
                "770053",
                null
        );
    }
}

