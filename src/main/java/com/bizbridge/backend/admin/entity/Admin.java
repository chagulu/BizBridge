package com.bizbridge.backend.admin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "admins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String passwordHash;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean twoFactorEnabled;
    private String twoFactorSecret;

    private LocalDateTime lastLoginAt;

    public enum Role {
        SUPER_ADMIN, SUB_ADMIN
    }
}
