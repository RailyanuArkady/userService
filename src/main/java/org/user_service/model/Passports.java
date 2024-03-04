package org.user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "passports")
public class Passports {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passport-sequence")
    @SequenceGenerator(name = "passport-sequence", allocationSize = 1)
    private Long id;
    @UuidGenerator
    @Column(name = "external_id")
    private UUID externalId;
    @Column(name = "passport_series")
    private String passportSeries;
    @Column(name = "passport_number")
    private String passportNumber;
    @Column(name = "passport_division_name")
    private String passportDivisionName;
    @Column(name = "passport_division_code")
    private String passportDivisionCode;
    @Column(name = "passport_date_of_issue")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date passportDateOfIssue;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;

}
