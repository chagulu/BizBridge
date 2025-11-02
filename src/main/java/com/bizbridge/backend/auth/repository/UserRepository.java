package com.bizbridge.backend.auth.repository;

import com.bizbridge.backend.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByMobileNoAndRole(String mobileNo, User.Role role);

    Optional<User> findByMobileNo(String mobileNo);
}
