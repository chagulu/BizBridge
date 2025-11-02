package com.bizbridge.backend.admin.service;

import com.bizbridge.backend.admin.dto.AdminLoginRequest;
import com.bizbridge.backend.admin.dto.AdminLoginResponse;
import com.bizbridge.backend.admin.dto.AdminProfileDto;
import com.bizbridge.backend.admin.entity.Admin;
import com.bizbridge.backend.admin.repository.AdminRepository;
import com.bizbridge.backend.auth.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AdminLoginResponse login(AdminLoginRequest request) {
        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPasswordHash())) {
            throw new RuntimeException("Invalid username or password");
        }

        // (Optional) Validate twoFactorCode if 2FA is enabled
        if (admin.isTwoFactorEnabled() && (request.getTwoFactorCode() == null || request.getTwoFactorCode().isEmpty())) {
            throw new RuntimeException("Two-factor code required");
        }

        // Generate JWT
        String token = jwtUtil.generateAdminToken(admin);

        return AdminLoginResponse.builder()
                .status("SUCCESS")
                .message("Login successful")
                .token(token)
                .admin(AdminProfileDto.fromEntity(admin))
                .build();
    }
}
