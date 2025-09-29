package com.example.userservice.service;

import com.example.userservice.dto.UserCreateRequest;
import com.example.userservice.entities.Passport;
import com.example.userservice.entities.Users;
import com.example.userservice.exception.PhoneAlreadyExistsException;
import com.example.userservice.repository.PassportRepository;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PassportRepository passportRepository;

    @Transactional
    public UUID createUser(UserCreateRequest request) {
        if (userRepository.existsByPhone(request.phone())) {
            throw new PhoneAlreadyExistsException(
                    "User with phone " + request.phone() + " already exists"
            );
        }
        Users user = new Users()
                .setExternalId(UUID.randomUUID())
                .setPhone(request.phone())
                .setEmail(request.email())
                .setSex(request.sex())
                .setPhotoUrl(request.photoId().toString())
                .setBirthdate(request.birthdate())
                .setIsDeleted(false);

        Users savedUser = userRepository.save(user);

        Passport passport = new Passport()
                .setExternalId(UUID.randomUUID())
                .setPassportSeries(request.passport().passportSeries())
                .setPassportNumber(request.passport().passportNumber())
                .setPassportDivisionName(request.passport().passportDivisionName())
                .setPassportDivisionCode(request.passport().passportDivisionCode())
                .setPassportDateOfIssue(request.passport().passportDateOfIssue())
                .setUser(savedUser);

        passportRepository.save(passport);

        return savedUser.getExternalId();
    }
}