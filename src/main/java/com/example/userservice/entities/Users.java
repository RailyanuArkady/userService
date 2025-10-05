package com.example.userservice.entities;

import com.example.userservice.enums.Sex;
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
@Builder
@EqualsAndHashCode(of = "externalId")
@Accessors(chain = true)
public class Users {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user-sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    private UUID externalId;
    private String phone;
    private String email;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    private UUID photoUrl;
    private boolean isDeleted = false;
    private LocalDate birthdate;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Passport> passports = new ArrayList<>();

}
