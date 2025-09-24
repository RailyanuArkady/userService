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
@Table(name = "users")
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
public class Users {
//    public enum Gender {
//        MALE,FEMALE
//    }
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    private UUID externalId;
    private String phone;
    private String email;
//    @Enumerated(value = EnumType.STRING)
    private String sex;
    private String photoUrl;
    private Boolean isDeleted = false;
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}
