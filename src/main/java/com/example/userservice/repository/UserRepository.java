package com.example.userservice.repository;

import com.example.userservice.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Optional<Users> findByExternalId(UUID externalId);
}