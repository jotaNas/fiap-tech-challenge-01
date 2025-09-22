package com.fiap.postech.techchallenger.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_email", columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = false)
    private String login;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private UserType type;

    @Embedded
    private Address address;

    @Column(nullable = false)
    private OffsetDateTime lastModifiedAt;

    @PrePersist
    public void prePersist() {
        this.lastModifiedAt = OffsetDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModifiedAt = OffsetDateTime.now();
    }
}
