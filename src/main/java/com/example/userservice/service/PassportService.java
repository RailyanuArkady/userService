package com.example.userservice.service;

import com.example.userservice.dto.PassportUpdateRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.entities.Users;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.mapper.PassportMapper;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PassportService {

    private final UserRepository userRepository;
    private final PassportMapper passportMapper;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserResponse updatePassport(UUID externalId, PassportUpdateRequest request) {
        Users user = userRepository.findByExternalId(externalId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + externalId));
        userMapper.updatePassport(request, user, passportMapper);
        return userMapper.toResponse(user, passportMapper);
    }
}

