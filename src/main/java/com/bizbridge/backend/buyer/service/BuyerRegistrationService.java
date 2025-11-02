package com.bizbridge.backend.buyer.service;

import com.bizbridge.backend.auth.repository.UserRepository;
import com.bizbridge.backend.auth.utils.JwtUtil;
import com.bizbridge.backend.buyer.dto.BuyerRegistrationRequest;
import com.bizbridge.backend.common.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuyerRegistrationService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public BuyerRegistrationService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Completes Buyer registration after OTP verification.
     * Updates full name, email, and marks user as verified.
     *
     * @param token   JWT token from OTP login
     * @param request Buyer registration details
     * @return updated User entity
     */
    public User completeRegistration(String token, BuyerRegistrationRequest request) {
        Claims claims = jwtUtil.validateTokenAndGetClaims(token);
        String mobileNo = claims.getSubject();

        // Fetch user by mobile number
        Optional<User> optionalUser = userRepository.findByMobileNo(mobileNo);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Buyer not found for mobile: " + mobileNo);
        }

        User user = optionalUser.get();

        // Role validation
        if (user.getRole() == null || !"BUYER".equalsIgnoreCase(user.getRole().name())) {
            throw new RuntimeException("Invalid role for buyer registration");
        }

        // Update profile info
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setVerified(true);
        userRepository.save(user);

        return user;
    }
}
