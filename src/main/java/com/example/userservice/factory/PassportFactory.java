package com.example.userservice.factory;


import com.example.userservice.entities.Passport;
import com.example.userservice.entities.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PassportFactory {


    public Passport enrichWithBusinessLogic(Passport passport, Users user) {
        return passport
                .setExternalId(UUID.randomUUID())
                .setUser(user);
    }
}

