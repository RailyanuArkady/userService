package com.example.userservice.mapper;

import com.example.userservice.dto.request.PassportCreateRequest;
import com.example.userservice.dto.request.UserCreateRequest;
import com.example.userservice.dto.request.UserUpdateRequest;
import com.example.userservice.dto.response.PassportResponse;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.entities.Passport;
import com.example.userservice.entities.Users;
import com.example.userservice.enums.Sex;
import com.example.userservice.utils.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.example.userservice.utils.TestDataFactory.createValidPassportRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @Mock
    private PassportMapper passportMapper;

    @InjectMocks
    private UserMapper userMapper = new UserMapperImpl();

    @Test
    @DisplayName("Map UserCreateRequest to User entity")
    void toEntity_ShouldMapUserCreateRequestToEntity() {
        UserCreateRequest request = TestDataFactory.createValidUserRequest();
        Users result = userMapper.toEntity(request);

        assertThat(result).isNotNull();
        assertThat(result.getPhone()).isEqualTo("+79161234567");
        assertThat(result.getSex()).isEqualTo(Sex.MALE);
        assertThat(result.getPhotoUrl()).isEqualTo(request.photoId());
        assertThat(result.getBirthdate()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(result.getEmail()).isEqualTo("ivan@mail.ru");
        assertThat(result.getExternalId()).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getCreatedAt()).isNull();
        assertThat(result.getModifiedAt()).isNull();
        assertThat(result.isDeleted()).isFalse();
        assertThat(result.getPassports()).isEmpty();
    }

    @Test
    @DisplayName("Map User entity to UserResponse with passport data")
    void toResponse_ShouldMapUserToResponseWithPassport() {

        UUID photoId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Users user = Users.builder()
                .id(1L)
                .externalId(userId)
                .phone("+79161234567")
                .sex(Sex.MALE)
                .photoUrl(photoId)
                .birthdate(LocalDate.of(1990, 1, 1))
                .email("ivan@mail.ru")
                .build();

        Passport passport = Passport.builder()
                .passportSeries("4510")
                .passportNumber("123456")
                .passportDivisionName("ОВД района")
                .passportDivisionCode("770053")
                .passportDateOfIssue(LocalDate.of(2020, 5, 15))
                .build();

        user.setPassports(List.of(passport));

        PassportResponse passportResponse = new PassportResponse(
                "4510", "123456", "ОВД района", "770053", LocalDate.of(2020, 5, 15)
        );

        when(passportMapper.toResponse(any(Passport.class))).thenReturn(passportResponse);
        UserResponse result = userMapper.toResponse(user, passportMapper);
        assertThat(result).isNotNull();
        assertThat(result.phone()).isEqualTo("+79161234567");
        assertThat(result.sex()).isEqualTo(Sex.MALE);
        assertThat(result.photoId()).isEqualTo(photoId);
        assertThat(result.birthdate()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(result.passport()).isEqualTo(passportResponse);
    }

    @Test
    @DisplayName("Update User entity from UserUpdateRequest")
    void updateUser_ShouldUpdateUserFromRequest() {
        Users user = Users.builder()
                .phone("old-phone")
                .build();
        UserUpdateRequest updateRequest = new UserUpdateRequest(
                "new-phone", Sex.MALE, UUID.randomUUID(), LocalDate.now()
        );
        userMapper.updateUser(updateRequest, user);
        System.out.println("Expected: new-phone, Actual: " + user.getPhone());
        assertThat(user.getPhone()).isEqualTo("new-phone");
    }

    @Test
    @DisplayName("Create User with Passport relation from request")
    void toResponsePass_ShouldCreateUserWithPassportRelation() {
        UUID photoId = UUID.randomUUID();
        UserCreateRequest request = new UserCreateRequest(
                "+79161234567", Sex.MALE, photoId, LocalDate.of(1990, 1, 1),
                "ivan@mail.ru", createValidPassportRequest()
        );
        Passport mockPassport = new Passport();
        when(passportMapper.toEntity(any(PassportCreateRequest.class))).thenReturn(mockPassport);
        Users result = userMapper.toResponsePass(request, passportMapper);
        assertThat(result).isNotNull();
        assertThat(result.getPassports()).hasSize(1);
        assertThat(result.getPassports().get(0)).isEqualTo(mockPassport);
        assertThat(result.getPassports().get(0).getUser()).isEqualTo(result);
    }

    @Test
    @DisplayName("Return first passport response from passports list")
    void mapPassports_ShouldReturnFirstPassportResponse_WhenUserHasPassport() {
        Passport passport = Passport.builder()
                .passportSeries("4510")
                .passportNumber("123456")
                .build();
        PassportResponse expectedResponse = TestDataFactory.createPassportResponse();
        when(passportMapper.toResponse(passport)).thenReturn(expectedResponse);
        PassportResponse result = userMapper.mapPassports(List.of(passport), passportMapper);
        assertThat(result).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Always include passport in user response")
    void toResponse_ShouldAlwaysIncludePassport_InUserResponse() {
        UUID photoId = UUID.randomUUID();
        Users user = Users.builder()
                .phone("+79161234567")
                .sex(Sex.MALE)
                .photoUrl(photoId)
                .birthdate(LocalDate.of(1990, 1, 1))
                .email("ivan@mail.ru")
                .build();
        Passport passport = Passport.builder()
                .passportSeries("4510")
                .passportNumber("123456")
                .build();
        user.setPassports(List.of(passport));
        PassportResponse passportResponse = new PassportResponse(
                "4510", "123456", "ОВД",
                "770053", LocalDate.of(2020, 5, 15)
        );
        when(passportMapper.toResponse(any(Passport.class))).thenReturn(passportResponse);
        UserResponse result = userMapper.toResponse(user, passportMapper);
        assertThat(result.passport()).isNotNull();
        assertThat(result.passport().passportSeries()).isEqualTo("4510");
        assertThat(result.passport().passportNumber()).isEqualTo("123456");
    }
}