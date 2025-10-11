package com.example.userservice.repository;

import com.example.userservice.entities.Users;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u FROM Users u WHERE u.externalId = :externalId AND u.isDeleted = false")
    Optional<Users> findByExternalId(@Param("externalId") UUID externalId);
}
