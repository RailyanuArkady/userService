package com.example.userservice.entities;

import com.example.userservice.enums.Sex;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
//могут возникнуть проблемы, потому что айди создается только после сохранения в базу, до сохранения в бд айди нет, есть только externalId
@EqualsAndHashCode(of = "id")
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
    private String photoUrl;
    @Builder.Default
    private Boolean isDeleted = false;
    private LocalDate birthdate;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Passport> passports = new ArrayList<>();
}
