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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user-sequence")
    @SequenceGenerator(name = "user-sequence", allocationSize = 1)
    private Long id;
    @UuidGenerator
    private UUID externalId;
    private String phone;
    private String email;
    @Enumerated(EnumType.STRING)
    private PersonSex sex;
    private String photoUrl;
    private boolean isDeleted;
    private LocalDate birthdate;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Passport passport;
}
