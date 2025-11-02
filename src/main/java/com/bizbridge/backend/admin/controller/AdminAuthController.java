package com.bizbridge.backend.admin.controller;

import com.bizbridge.backend.admin.dto.AdminLoginRequest;
import com.bizbridge.backend.admin.dto.AdminLoginResponse;
import com.bizbridge.backend.admin.service.AdminAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @PostMapping("/login")
    public ResponseEntity<AdminLoginResponse> login(@RequestBody AdminLoginRequest request) {
        return ResponseEntity.ok(adminAuthService.login(request));
    }
}
