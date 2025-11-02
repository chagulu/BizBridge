package com.bizbridge.backend.admin.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLoginResponse {
    private String status;
    private String message;
    private String token;
    private AdminProfileDto admin;
}
