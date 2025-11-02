package com.bizbridge.backend.common.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String mobileNo;

    @Column(unique = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;  // BUYER, SELLER, ADMIN

    private String otpCode;
    private LocalDateTime otpExpiry;

    private String jwtToken;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private PlanType planType = PlanType.BASIC;

    // ✅ NEW: Track if user completed registration
    @Column(name = "is_verified")
    private Boolean verified = false;

    // ✅ NEW: Track how the user was registered
    @Enumerated(EnumType.STRING)
    private RegistrationType registrationType = RegistrationType.MOBILE;

    @Enumerated(EnumType.STRING)
    private RegistrationSource registrationSource = RegistrationSource.WEB;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        status = Status.ACTIVE;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ===============================
    // ENUMS
    // ===============================

    public enum Role {
        BUYER, SELLER, ADMIN
    }

    public enum Status {
        ACTIVE, BLOCKED
    }

    public enum PlanType {
        BASIC, PREMIUM
    }

    public enum RegistrationType {
        WEB, MOBILE, ADMIN
    }

    public enum RegistrationSource {
        WEB, APP, ADMIN
    }
}
