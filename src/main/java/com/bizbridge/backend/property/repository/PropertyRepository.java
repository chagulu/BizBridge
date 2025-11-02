package com.bizbridge.backend.property.repository;

import com.bizbridge.backend.property.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    // By city via address relation
    @Query("""
        SELECT p FROM Property p 
        JOIN UserAddress a ON p.addressId = a.id 
        WHERE LOWER(a.city) = LOWER(:city) 
          AND p.isActive = true 
          AND p.approvalStatus = 'APPROVED'
        """)
    List<Property> findByCity(String city);

    // By pincode
    @Query("""
        SELECT p FROM Property p 
        JOIN UserAddress a ON p.addressId = a.id 
        WHERE a.pincode = :pincode 
          AND p.isActive = true 
          AND p.approvalStatus = 'APPROVED'
        """)
    List<Property> findByPincode(String pincode);
}
