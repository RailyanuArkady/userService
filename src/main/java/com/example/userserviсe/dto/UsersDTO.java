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
@Table(name = "users")
@Setter
@Getter
public class UsersDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "external_id", unique = true, nullable = false)
    private UUID externalId;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "sex")
    private String sex;
    @Column(name = "photo_url")
    private String photoUrl;
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public UsersDTO() {
    }

    public UsersDTO(Long id, UUID externalId, String phone, String email, String sex, String photoUrl, Boolean isDeleted,
                    Date birthdate, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.externalId = externalId;
        this.phone = phone;
        this.email = email;
        this.sex = sex;
        this.photoUrl = photoUrl;
        this.isDeleted = isDeleted;
        this.birthdate = birthdate;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
