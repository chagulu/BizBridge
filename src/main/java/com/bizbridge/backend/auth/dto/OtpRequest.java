package com.bizbridge.backend.auth.dto;

import com.bizbridge.backend.common.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtpRequest {
    private String mobileNo;
    private User.Role role;  // BUYER or SELLER
}
