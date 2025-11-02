package com.bizbridge.backend.auth.dto;

import com.bizbridge.backend.common.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String status;
    private String token;
    private UserProfileDto user;
}
