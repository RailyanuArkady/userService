package org.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.user_service.model.Passport;

@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {
}
