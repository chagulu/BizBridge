package com.bizbridge.backend.buyer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyerRegistrationRequest {
    private String fullName;
    private String email;
    private String preferredCity;
    private String interestedCategory;

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
