package com.bizbridge.backend.property.service;

import com.bizbridge.backend.auth.repository.UserRepository;
import com.bizbridge.backend.auth.utils.JwtUtil;
import com.bizbridge.backend.common.entity.User;
import com.bizbridge.backend.common.entity.UserAddress;
import com.bizbridge.backend.common.repository.UserAddressRepository;
import com.bizbridge.backend.property.entity.Property;
import com.bizbridge.backend.property.repository.PropertyRepository;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyListingService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final UserAddressRepository addressRepository;
    private final JwtUtil jwtUtil;

    public PropertyListingService(PropertyRepository propertyRepository,
                                  UserRepository userRepository,
                                  UserAddressRepository addressRepository,
                                  JwtUtil jwtUtil) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<Property> getPropertiesForBuyer(String token, String city, String pincode) {
        Claims claims = jwtUtil.validateTokenAndGetClaims(token);
        String mobileNo = claims.getSubject();

        User buyer = userRepository.findByMobileNo(mobileNo)
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        // Use buyer’s saved address if no filter provided
        if ((city == null || city.isEmpty()) && (pincode == null || pincode.isEmpty())) {
            UserAddress addr = addressRepository.findByUserId(buyer.getId())
                    .stream()
                    .filter(UserAddress::getIsPrimary)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Buyer address not found"));
            city = addr.getCity();
            pincode = addr.getPincode();
        }

        // Priority: Pincode > City
        if (pincode != null && !pincode.isEmpty()) {
            return propertyRepository.findByPincode(pincode);
        } else if (city != null && !city.isEmpty()) {
            return propertyRepository.findByCity(city);
        } else {
            throw new RuntimeException("No valid location found for property search");
        }
    }
}
