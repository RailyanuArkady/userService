package com.example.userservice.repository;


import com.example.userservice.entities.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportRepository extends JpaRepository<Passport, Long> {
    boolean existsByPassportSeriesAndPassportNumber(String passportSeries, String passportNumber);
}
