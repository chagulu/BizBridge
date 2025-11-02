package com.bizbridge.backend.common.repository;

import com.bizbridge.backend.common.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

    // Find all addresses by user ID
    List<UserAddress> findByUserId(Long userId);

    // Find primary address (if applicable)
    List<UserAddress> findByUserIdAndIsPrimaryTrue(Long userId);
}
