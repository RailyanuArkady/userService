package com.example.userservice.factory;

import com.example.userservice.entities.Users;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserFactory {

    public Users enrichWithBusinessLogic(Users user) {
        return user
                .setExternalId(UUID.randomUUID())
                .setIsDeleted(false);
    }
}
