package com.bizbridge.backend.admin.dto;

import com.bizbridge.backend.admin.entity.Admin;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminProfileDto {
    private Long id;
    private String username;
    private String email;
    private String role;
    private boolean twoFactorEnabled;

    public static AdminProfileDto fromEntity(Admin admin) {
        return AdminProfileDto.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .role(admin.getRole().name())
                .twoFactorEnabled(admin.isTwoFactorEnabled())
                .build();
    }
}
