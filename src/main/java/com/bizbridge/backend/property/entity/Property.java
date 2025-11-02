package com.bizbridge.backend.property.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "properties")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sellerId;
    private Long addressId;
    private String title;
    private String description;
    private String businessType;

    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;

    private Long categoryId;
    private Double price;
    private String currency;
    private Integer establishedYear;
    private Double totalAreaSqft;
    private Integer employeesCount;
    private Double monthlyRevenue;
    private Double profitMargin;

    @Column(columnDefinition = "JSON")
    private String imageUrls;

    @Column(columnDefinition = "JSON")
    private String videoUrls;

    private Boolean isActive;
    private String approvalStatus;
    private LocalDateTime listedAt;
    private LocalDateTime approvedAt;
    private String rejectedReason;
    private Long createdBy;
    private Long updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum PropertyType {
        SILVER, GOLD, PLATINUM
    }
}
