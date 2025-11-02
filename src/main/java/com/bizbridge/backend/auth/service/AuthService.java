package com.bizbridge.backend.auth.service;

import com.bizbridge.backend.auth.dto.LoginResponse;
import com.bizbridge.backend.auth.dto.OtpRequest;
import com.bizbridge.backend.auth.dto.UserProfileDto;
import com.bizbridge.backend.auth.dto.VerifyOtpRequest;
import com.bizbridge.backend.auth.repository.UserRepository;
import com.bizbridge.backend.auth.utils.JwtUtil;
import com.bizbridge.backend.auth.utils.OtpUtil;
import com.bizbridge.backend.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public String sendOtp(OtpRequest request) {
        User user = userRepository.findByMobileNoAndRole(request.getMobileNo(), request.getRole())
                .orElseGet(() -> {
                    // Auto-register new user if not exist
                    User newUser = User.builder()
                            .mobileNo(request.getMobileNo())
                            .role(request.getRole())
                            .fullName("User_" + request.getMobileNo())
                            .build();
                    return userRepository.save(newUser);
                });

        String otp = OtpUtil.generateOtp();
        user.setOtpCode(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);

        // TODO: Integrate SMS Gateway (e.g., Twilio/Msg91)
        System.out.println("Generated OTP for " + user.getMobileNo() + ": " + otp);

        return "OTP sent successfully to " + user.getMobileNo();
    }

    public LoginResponse verifyOtp(VerifyOtpRequest request) {
    User user = userRepository.findByMobileNo(request.getMobileNo())
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (user.getOtpCode() == null ||
        !user.getOtpCode().equals(request.getOtp()) ||
        user.getOtpExpiry().isBefore(LocalDateTime.now())) {
        throw new RuntimeException("Invalid or expired OTP");
    }

    // OTP is valid — generate JWT
    String token = jwtUtil.generateToken(user);
    user.setJwtToken(token);
    user.setOtpCode(null);
    userRepository.save(user);

    // Create Response DTO
    LoginResponse response = LoginResponse.builder()
            .status("SUCCESS")
            .token(token)
            .user(UserProfileDto.fromEntity(user))
            .build();

    return response;
}

}
