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

    // ✅ Track if user completed registration
    @Column(name = "is_verified")
    private Boolean verified = false;

    // ✅ Track how the user registered (BUYER, SELLER, WEB, MOBILE, ADMIN)
    @Enumerated(EnumType.STRING)
    private RegistrationType registrationType = RegistrationType.WEB;

    // ✅ Track from where user registered (web portal, mobile app, etc.)
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

    /**
     * RegistrationType indicates *what kind* of user registration flow was followed.
     * We include BUYER and SELLER to match OTP-based registration flow.
     */
    public enum RegistrationType {
        WEB, MOBILE, ADMIN, BUYER, SELLER
    }

    /**
     * RegistrationSource tracks the *channel* through which user signed up.
     */
    public enum RegistrationSource {
        WEB, APP, ADMIN
    }
}
