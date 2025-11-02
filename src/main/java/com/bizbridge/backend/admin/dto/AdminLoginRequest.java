package com.bizbridge.backend.admin.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLoginRequest {
    private String username;
    private String password;
    private String twoFactorCode;  // optional
}
