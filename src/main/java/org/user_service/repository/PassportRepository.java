package org.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.user_service.model.Passport;
import org.user_service.model.User;

public interface PassportRepository extends JpaRepository<Passport, Long> {
    @Query("SELECT u.id FROM User u WHERE id = ?1")
    User getUserById(Long userId);
}
