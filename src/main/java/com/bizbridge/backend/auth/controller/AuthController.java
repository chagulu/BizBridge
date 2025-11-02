package com.bizbridge.backend.auth.controller;

import com.bizbridge.backend.auth.dto.OtpRequest;
import com.bizbridge.backend.auth.dto.VerifyOtpRequest;
import com.bizbridge.backend.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/request-otp")
    public ResponseEntity<?> requestOtp(@RequestBody OtpRequest request) {
        return ResponseEntity.ok(authService.sendOtp(request));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
        String token = authService.verifyOtp(request);
        return ResponseEntity.ok()
                .body("{\"status\":\"SUCCESS\", \"token\":\"" + token + "\"}");
    }
}
