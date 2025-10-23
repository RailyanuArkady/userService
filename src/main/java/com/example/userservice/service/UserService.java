package com.example.userservice.service;

import com.example.userservice.dto.projection.UserProjection;
import com.example.userservice.dto.request.UserCreateRequest;
import com.example.userservice.dto.request.UserUpdateRequest;
import com.example.userservice.dto.response.UserResponse;
import com.example.userservice.entities.Users;
import com.example.userservice.exception.UserNotFoundException;
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

    @Transactional(readOnly = true)
    public UserProjection getUser(UUID externalId) {
        return userRepository.findByExternalIdAndIsDeletedFalse(externalId).orElseThrow(()
                -> new UserNotFoundException(String.format("User not found with id: %s", externalId)));
    }

    @Transactional
    public UserResponse updateUser(UUID externalId, UserUpdateRequest request) {
        Users user = userRepository.findByExternalId(externalId).orElseThrow(()
                -> new UserNotFoundException(String.format("User not found with id: %s", externalId)));
        userMapper.updateUser(request, user);
        return userMapper.toResponse(user, passportMapper);
    }

    @Transactional
    public void deleteUser(UUID externalId) {
        Users user = userRepository.findByExternalId(externalId).orElseThrow(()
                -> new UserNotFoundException(String.format("User not found with id: %s", externalId)));
        user.setDeleted(true);
    }


}