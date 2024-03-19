package org.user_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

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
//@SQLDelete(sql = "UPDATE users SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user-sequence")
    @SequenceGenerator(name = "user-sequence", allocationSize = 1)
    private Long id;
    private UUID externalId;
    private String phone;
    @Enumerated(EnumType.STRING)
    private PersonSex sex;
    private UUID photoId;
    private boolean isDeleted;
    private LocalDate birthdate;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Passport passport;

    public void setPassport(Passport passport) {
        this.passport = passport;
        passport.setUser(this);
    }

    public void removePassport() {
        if (this.passport != null) {
            this.passport.setUser(null);
            this.passport = null;
        }
    }
}
