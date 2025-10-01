package com.example.userservice.validation;

import com.example.userservice.dto.PassportCreateRequest;
import com.example.userservice.dto.UserCreateRequest;
import com.example.userservice.exception.PassportAlreadyExistsException;
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
        validateEmailFormat(request.email());
        validatePhoneFormat(request.phone());
        validatePassportFormat(request.passport());
    }

    private void validateEmailFormat(String email) {
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    private void validatePhoneFormat(String phone) {
        if (phone != null && !phone.matches("\\+?\\d{10,15}")) {
            throw new IllegalArgumentException("Invalid phone format");
        }
    }

    private void validatePassportFormat(PassportCreateRequest passport) {
        if (!passport.passportSeries().matches("\\d{4}")) {
            throw new PassportAlreadyExistsException(
                    "Invalid passport series: must be 4 digits"
            );
        }
        if (!passport.passportNumber().matches("\\d{6}")) {
            throw new PassportAlreadyExistsException(
                    "Invalid passport number: must be 6 digits"
            );
        }
    }

}

