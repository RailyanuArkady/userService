package org.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.user_service.model.Passport;

public interface PassportRepository extends JpaRepository<Passport, Long> {
}
