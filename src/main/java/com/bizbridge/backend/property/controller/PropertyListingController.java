package com.bizbridge.backend.property.controller;

import com.bizbridge.backend.property.entity.Property;
import com.bizbridge.backend.property.service.PropertyListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/property")
public class PropertyListingController {

    private final PropertyListingService propertyListingService;

    public PropertyListingController(PropertyListingService propertyListingService) {
        this.propertyListingService = propertyListingService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> listProperties(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String pincode
    ) {
        String token = authHeader.replace("Bearer ", "");
        List<Property> result = propertyListingService.getPropertiesForBuyer(token, city, pincode);
        return ResponseEntity.ok(result);
    }
}
