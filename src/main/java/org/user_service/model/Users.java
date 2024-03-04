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
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user-sequence")
    @SequenceGenerator(name = "user-sequence", allocationSize = 1)
    private Long id;
    @UuidGenerator
    @Column(name = "external_id")
    private UUID externalId;
    private String phone;
    private String email;
    private String sex;
    @Column(name = "photo_url")
    private String photoUrl;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private Date birthDate;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Passports passports;
}
