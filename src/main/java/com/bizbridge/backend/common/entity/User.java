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
    private Role role;  // BUYER or SELLER

    private String otpCode;
    private LocalDateTime otpExpiry;

    private String jwtToken;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private PlanType planType = PlanType.BASIC;

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

    public enum Role {
        BUYER, SELLER, ADMIN
    }

    public enum Status {
        ACTIVE, BLOCKED
    }

    public enum PlanType {
    BASIC, PREMIUM
    }
}
