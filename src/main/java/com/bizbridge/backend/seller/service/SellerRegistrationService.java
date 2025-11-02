package com.bizbridge.backend.seller.service;

import com.bizbridge.backend.auth.repository.UserRepository;
import com.bizbridge.backend.auth.utils.JwtUtil;
import com.bizbridge.backend.common.entity.User;
import com.bizbridge.backend.seller.dto.SellerRegistrationRequest;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerRegistrationService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public SellerRegistrationService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Completes Seller registration after OTP verification.
     * Updates name, email, and marks seller as verified.
     *
     * @param token   JWT token from OTP login
     * @param request Seller registration details
     * @return updated User entity
     */
    public User completeRegistration(String token, SellerRegistrationRequest request) {
        Claims claims = jwtUtil.validateTokenAndGetClaims(token);
        String mobileNo = claims.getSubject();

        // Fetch user by mobile number
        Optional<User> optionalUser = userRepository.findByMobileNo(mobileNo);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Seller not found for mobile: " + mobileNo);
        }

        User user = optionalUser.get();

        // Role validation
        if (user.getRole() == null || !"SELLER".equalsIgnoreCase(user.getRole().name())) {
            throw new RuntimeException("Invalid role for seller registration");
        }

        // Update seller profile info
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setVerified(true);
        userRepository.save(user);

        return user;
    }
}
