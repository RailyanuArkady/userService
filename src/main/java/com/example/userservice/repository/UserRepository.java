package com.example.userservice.repository;

import com.example.userservice.entities.Users;
import com.example.userservice.exception.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByExternalId(UUID externalId);

    default Users findOrThrow(UUID externalId) {
        return findByExternalId(externalId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + externalId));
    }
}