package com.example.userservice.factory;

import com.example.userservice.dto.UserCreateRequest;
import com.example.userservice.entities.Users;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserFactory {

    public Users createUser(UserCreateRequest request) {
        return new Users()
                .setExternalId(UUID.randomUUID())
                .setPhone(request.phone())
                .setEmail(request.email())
                .setSex(request.sex())
                .setPhotoUrl(request.photoId().toString())
                .setBirthdate(request.birthdate())
                .setIsDeleted(false);
    }
}
