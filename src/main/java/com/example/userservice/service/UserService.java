package com.example.userservice.service;

import com.example.userservice.dto.PassportUpdateRequest;
import com.example.userservice.dto.UserCreateRequest;
import com.example.userservice.dto.UserResponse;
import com.example.userservice.dto.UserUpdateRequest;
import com.example.userservice.entities.Users;
import com.example.userservice.mapper.PassportMapper;
import com.example.userservice.mapper.UserMapper;
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
    private final UserMapper userMapper;
    private final PassportMapper passportMapper;

    @Transactional
    public UUID createUser(UserCreateRequest request) {
        Users user = userMapper.toResponsePass(request, passportMapper);
        return userRepository.save(user).getExternalId();
    }

    public UserResponse getUserByExternalId(UUID externalId) {
        Users user = userRepository.findOrThrow(externalId);
        return userMapper.toResponse(user, passportMapper);
    }

    @Transactional
    public UserResponse updateUser(UUID externalId, UserUpdateRequest request) {
        Users user = userRepository.findOrThrow(externalId);
        userMapper.updateUser(request, user);
        return userMapper.toResponse(user, passportMapper);
    }

    @Transactional
    public UserResponse updatePassport(UUID externalId, PassportUpdateRequest request) {
        Users user = userRepository.findOrThrow(externalId);
        userMapper.updatePassport(request, user, passportMapper);
        return userMapper.toResponse(user, passportMapper);
    }

}