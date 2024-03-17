package org.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.user_service.model.User;

import java.time.LocalDate;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "WITH updated_user AS (" +
            "  UPDATE users " +
            "  SET phone = :phone," +
            "      sex = :sex," +
            "      photo_id = :photoId," +
            "      birthdate = :birthdate" +
            "  WHERE id = :id" +
            "  RETURNING id" +
            ")," +
            "updated_passport AS (" +
            "  UPDATE passports " +
            "  SET passport_series = :passportSeries," +
            "      passport_number = :passportNumber," +
            "      passport_division_name = :passportDivisionName," +
            "      passport_division_code = :passportDivisionCode," +
            "      passport_date_of_issue = :passportDateOfIssue" +
            "  WHERE user_id = :id" +
            "  RETURNING user_id" +
            ")" +
            " SELECT * FROM updated_user;"
            , nativeQuery = true)
    void updateUserAndPassport(@Param("id") Long id,
                               @Param("phone") String phone,
                               @Param("sex") String sex,
                               @Param("photoId") UUID photoId,
                               @Param("birthdate") LocalDate birthdate,
                               @Param("passportSeries") String passportSeries,
                               @Param("passportNumber") String passportNumber,
                               @Param("passportDivisionName") String passportDivisionName,
                               @Param("passportDivisionCode") String passportDivisionCode,
                               @Param("passportDateOfIssue") LocalDate passportDateOfIssue);
}
