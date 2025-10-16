package com.example.userservice.dto.projection;

import com.example.userservice.enums.Sex;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.util.UUID;

@JsonPropertyOrder({"phone", "sex", "photoId", "birthdate", "email", "passport"})
public interface UserProjection {
    String getPhone();

    Sex getSex();

    @Value("#{target.photoUrl}")
    UUID getPhotoId();

    LocalDate getBirthdate();

    String getEmail();

    @Value("#{target.passports[0]}")
    PassportProjection getPassport();

}
