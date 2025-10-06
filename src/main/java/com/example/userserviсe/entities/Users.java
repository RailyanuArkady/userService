package com.example.userserviсe.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
public class Users {
    @OneToMany(mappedBy = "user")
    private List<Passport> passports = new ArrayList<>();
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user-sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    private UUID externalId;
    private String phone;
    private String email;
    private String sex;
    private String photoUrl;
    private Boolean isDeleted = false;
    private LocalDate birthdate;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;

}
