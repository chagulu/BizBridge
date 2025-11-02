package com.bizbridge.backend.seller.controller;

import com.bizbridge.backend.seller.dto.SellerRegistrationRequest;
import com.bizbridge.backend.seller.service.SellerRegistrationService;
import com.bizbridge.backend.common.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
public class SellerRegistrationController {

    private final SellerRegistrationService registrationService;

    public SellerRegistrationController(SellerRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerSeller(@RequestHeader("Authorization") String authHeader,
                                            @RequestBody SellerRegistrationRequest request) {
        String token = authHeader.replace("Bearer ", "");
        User user = registrationService.completeRegistration(token, request);
        return ResponseEntity.ok().body(
                """
                {
                  "status": "SUCCESS",
                  "message": "Seller registration completed successfully",
                  "user": {
                    "id": %d,
                    "fullName": "%s",
                    "mobileNo": "%s",
                    "email": "%s",
                    "role": "%s"
                  }
                }
                """.formatted(
                        user.getId(),
                        user.getFullName(),
                        user.getMobileNo(),
                        user.getEmail(),
                        user.getRole()
                )
        );
    }
}
