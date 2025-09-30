package com.example.userservice.service;

import com.example.userservice.dto.PassportCreateRequest;
import com.example.userservice.dto.UserCreateRequest;
import com.example.userservice.entities.Passport;
import com.example.userservice.entities.Users;
import com.example.userservice.exception.EmailAlreadyExistsException;
import com.example.userservice.exception.PassportAlreadyExistsException;
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
        validateEmailUniqueness(request.email());
        validatePhoneUniqueness(request.phone());
        validatePassportUniqueness(request.passport());

        Users user = createAndSaveUser(request);
        createAndSavePassport(request, user);

        return user.getExternalId();
    }

    private void validatePhoneUniqueness(String phone) {
        if (userRepository.existsByPhone(phone)) {
            throw new PhoneAlreadyExistsException(
                    "User with phone " + phone + " already exists"
            );
        }
    }

    private void validateEmailUniqueness(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(
                    "User with email " + email + " already exists"
            );
        }
    }

    private void validatePassportUniqueness(PassportCreateRequest passport) {
        boolean passportExists = passportRepository.existsByPassportSeriesAndPassportNumber(
                passport.passportSeries(),
                passport.passportNumber()
        );

        if (passportExists) {
            throw new PassportAlreadyExistsException(
                    "Passport with series " + passport.passportSeries() +
                            " and number " + passport.passportNumber() + " already exists"
            );
        }
    }

    private Users createAndSaveUser(UserCreateRequest request) {
        Users user = buildUserFromRequest(request);
        return userRepository.save(user);
    }

    private Users buildUserFromRequest(UserCreateRequest request) {
        return new Users()
                .setExternalId(UUID.randomUUID())
                .setPhone(request.phone())
                .setEmail(request.email())
                .setSex(request.sex())
                .setPhotoUrl(request.photoId().toString())
                .setBirthdate(request.birthdate())
                .setIsDeleted(false);
    }

    private void createAndSavePassport(UserCreateRequest request, Users user) {
        Passport passport = buildPassportFromRequest(request, user);
        passportRepository.save(passport);
    }

    private Passport buildPassportFromRequest(UserCreateRequest request, Users user) {
        return new Passport()
                .setExternalId(UUID.randomUUID())
                .setPassportSeries(request.passport().passportSeries())
                .setPassportNumber(request.passport().passportNumber())
                .setPassportDivisionName(request.passport().passportDivisionName())
                .setPassportDivisionCode(request.passport().passportDivisionCode())
                .setPassportDateOfIssue(request.passport().passportDateOfIssue())
                .setUser(user);
    }
}