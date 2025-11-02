package com.bizbridge.backend.buyer.controller;

import com.bizbridge.backend.buyer.dto.BuyerRegistrationRequest;
import com.bizbridge.backend.buyer.service.BuyerRegistrationService;
import com.bizbridge.backend.common.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyer")
public class BuyerRegistrationController {

    private final BuyerRegistrationService registrationService;

    public BuyerRegistrationController(BuyerRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerBuyer(@RequestHeader("Authorization") String authHeader,
                                           @RequestBody BuyerRegistrationRequest request) {
        String token = authHeader.replace("Bearer ", "");
        User user = registrationService.completeRegistration(token, request);
        return ResponseEntity.ok().body(
                """
                {
                  "status": "SUCCESS",
                  "message": "Buyer registration completed successfully",
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
