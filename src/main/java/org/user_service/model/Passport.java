package org.user_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "passports")
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passport-sequence")
    @SequenceGenerator(name = "passport-sequence", allocationSize = 1)
    private Long id;
    @UuidGenerator
    private UUID externalId;
    private String passportSeries;
    private String passportNumber;
    private String passportDivisionName;
    private String passportDivisionCode;
    private LocalDate passportDateOfIssue;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    @OneToOne(fetch = FetchType.LAZY)
    private User user;


}
