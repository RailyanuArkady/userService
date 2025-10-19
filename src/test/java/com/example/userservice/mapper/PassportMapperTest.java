package com.example.userservice.mapper;

import com.example.userservice.dto.request.PassportCreateRequest;
import com.example.userservice.dto.request.PassportUpdateRequest;
import com.example.userservice.dto.response.PassportResponse;
import com.example.userservice.entities.Passport;
import com.example.userservice.utils.TestDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PassportMapperTest {

    private final PassportMapper passportMapper = Mappers.getMapper(PassportMapper.class);

    @Test
    @DisplayName("Map PassportCreateRequest to Passport entity")
    void toEntity_ShouldMapPassportCreateRequestToEntity() {
        PassportCreateRequest request = TestDataFactory.createValidPassportRequest();
        Passport result = passportMapper.toEntity(request);
        assertThat(result).isNotNull();
        assertThat(result.getPassportSeries()).isEqualTo("4510");
        assertThat(result.getPassportNumber()).isEqualTo("123456");
        assertThat(result.getPassportDivisionName()).isEqualTo("ОВД района");
        assertThat(result.getPassportDivisionCode()).isEqualTo("770053");
        assertThat(result.getPassportDateOfIssue()).isEqualTo(LocalDate.of(2020, 5, 15));
        assertThat(result.getExternalId()).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getUser()).isNull();
        assertThat(result.getCreatedAt()).isNull();
        assertThat(result.getModifiedAt()).isNull();
    }

    @Test
    @DisplayName("Update Passport entity from PassportUpdateRequest")
    void updateRequest_ShouldUpdatePassportFromRequest() {
        Passport passport = Passport.builder()
                .passportSeries("4510")
                .passportNumber("123456")
                .passportDivisionName("Старое подразделение")
                .passportDivisionCode("770053")
                .passportDateOfIssue(LocalDate.of(2020, 5, 15))
                .build();
        PassportUpdateRequest updateRequest = TestDataFactory.createValidPassportUpdateRequest();
        passportMapper.updateRequest(updateRequest, passport);
        assertThat(passport.getPassportSeries()).isEqualTo("4511");
        assertThat(passport.getPassportNumber()).isEqualTo("654321");
        assertThat(passport.getPassportDivisionName()).isEqualTo("Новое подразделение");
        assertThat(passport.getPassportDivisionCode()).isEqualTo("770054");
        assertThat(passport.getPassportDateOfIssue()).isEqualTo(LocalDate.of(2021, 6, 20));
    }

    @Test
    @DisplayName("Map Passport entity to PassportResponse DTO")
    void toResponse_ShouldMapPassportToResponse() {
        Passport passport = Passport.builder()
                .id(1L)
                .externalId(UUID.randomUUID())
                .passportSeries("4510")
                .passportNumber("123456")
                .passportDivisionName("ОВД района")
                .passportDivisionCode("770053")
                .passportDateOfIssue(LocalDate.of(2020, 5, 15))
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
        PassportResponse result = passportMapper.toResponse(passport);
        assertThat(result).isNotNull();
        assertThat(result.passportSeries()).isEqualTo("4510");
        assertThat(result.passportNumber()).isEqualTo("123456");
        assertThat(result.passportDivisionName()).isEqualTo("ОВД района");
        assertThat(result.passportDivisionCode()).isEqualTo("770053");
        assertThat(result.passportDateOfIssue()).isEqualTo(LocalDate.of(2020, 5, 15));
    }
}