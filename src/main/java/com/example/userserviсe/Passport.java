package com.example.userserviсe;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "passports")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
public class Passport {
    @Id
    @SequenceGenerator(name = "passport_sequence", sequenceName = "passport_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passport_sequence")
    private Long id;
    private UUID externalId;
    private String passportSeries;
    private String passportNumber;
    private String passportDivisionName;
    private String passportDivisionCode;
    @Temporal(TemporalType.DATE)
    private Date passportDateOfIssue;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    private Long userId;
}
