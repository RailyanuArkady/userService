package com.example.userserviсe.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "passports")
@Getter
@Setter
public class PassportDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "external_id", unique = true)
    private UUID externalId;
    @Column(name = "passport_series", length = 4)
    private String passportSeries;
    @Column(name = "passport_number", length = 6)
    private String passportNumber;
    @Column(name = "passport_division_name")
    private String passportDivisionName;
    @Column(name = "passport_division_code", length = 6)
    private String passportDivisionCode;
    @Column(name = "passport_date_of_issue")
    @Temporal(TemporalType.DATE)
    private Date passportDateOfIssue;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
    @Column(name = "user_id")
    private Long userId;

    public PassportDTO() {
    }

    public PassportDTO(Long id, UUID externalId, String passportSeries, String passportNumber, String passportDivisionName,
                       String passportDivisionCode, Date passportDateOfIssue, LocalDateTime createdAt, LocalDateTime modifiedAt, Long userId) {
        this.id = id;
        this.externalId = externalId;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
        this.passportDivisionName = passportDivisionName;
        this.passportDivisionCode = passportDivisionCode;
        this.passportDateOfIssue = passportDateOfIssue;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.userId = userId;
    }
}
