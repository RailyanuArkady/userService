package com.example.userservice.service;

import com.example.userservice.dto.UserCreateRequest;
import com.example.userservice.entities.Passport;
import com.example.userservice.entities.Users;
import com.example.userservice.factory.PassportFactory;
import com.example.userservice.factory.UserFactory;
import com.example.userservice.repository.PassportRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PassportRepository passportRepository;
    private final ValidationService validationService;
    private final UserFactory userFactory;
    private final PassportFactory passportFactory;

    @Transactional
    public UUID createUser(UserCreateRequest request) {
        validationService.validateUserCreation(request);

        Users user = userFactory.createUser(request);
        Users savedUser = userRepository.save(user);

        Passport passport = passportFactory.createPassport(request.passport(), savedUser);
        passportRepository.save(passport);

        return savedUser.getExternalId();
    }
}