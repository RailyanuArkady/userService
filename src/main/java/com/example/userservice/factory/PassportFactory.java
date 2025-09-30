package com.example.userservice.factory;


import com.example.userservice.dto.PassportCreateRequest;
import com.example.userservice.entities.Passport;
import com.example.userservice.entities.Users;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PassportFactory {

    public Passport createPassport(PassportCreateRequest passportRequest, Users user) {
        return new Passport()
                .setExternalId(UUID.randomUUID())
                .setPassportSeries(passportRequest.passportSeries())
                .setPassportNumber(passportRequest.passportNumber())
                .setPassportDivisionName(passportRequest.passportDivisionName())
                .setPassportDivisionCode(passportRequest.passportDivisionCode())
                .setPassportDateOfIssue(passportRequest.passportDateOfIssue())
                .setUser(user);
    }
}