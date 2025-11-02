package com.bizbridge.backend.auth.dto;

import com.bizbridge.backend.common.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {
    private Long id;
    private String fullName;
    private String mobileNo;
    private String email;
    private String role;
    private String planType;

    public static UserProfileDto fromEntity(User user) {
        return UserProfileDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .mobileNo(user.getMobileNo())
                .email(user.getEmail())
                .role(user.getRole().name())
                .planType(user.getPlanType() != null ? user.getPlanType().name() : "BASIC")
                .build();
    }
}
