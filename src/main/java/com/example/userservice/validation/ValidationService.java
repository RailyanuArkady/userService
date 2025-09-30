package com.example.userservice.validation;

import com.example.userservice.dto.PassportCreateRequest;
import com.example.userservice.dto.UserCreateRequest;
import com.example.userservice.exception.EmailAlreadyExistsException;
import com.example.userservice.exception.PassportAlreadyExistsException;
import com.example.userservice.exception.PhoneAlreadyExistsException;
import com.example.userservice.repository.PassportRepository;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final UserRepository userRepository;
    private final PassportRepository passportRepository;

    public void validateUserCreation(UserCreateRequest request) {
        validateEmailUniqueness(request.email());
        validatePhoneUniqueness(request.phone());
        validatePassportUniqueness(request.passport());
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
        if (passportRepository.existsByPassportSeriesAndPassportNumber(
                passport.passportSeries(),
                passport.passportNumber()
        )) {
            throw new PassportAlreadyExistsException(
                    "Passport with series " + passport.passportSeries() +
                            " and number " + passport.passportNumber() + " already exists"
            );
        }
    }
}
