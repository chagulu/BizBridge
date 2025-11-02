package com.bizbridge.backend.seller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerRegistrationRequest {
    private String fullName;
    private String email;
    private String businessName;
    private String gstNumber;
    private String websiteUrl;

    // Address fields
    private String addressLine1;
    private String addressLine2;
    private String landmark;
    private String city;
    private String state;
    private String country;
    private String pincode;
    private Double latitude;
    private Double longitude;
}
