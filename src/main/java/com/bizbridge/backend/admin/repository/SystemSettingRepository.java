package com.bizbridge.backend.admin.repository;

import com.bizbridge.backend.admin.entity.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemSettingRepository extends JpaRepository<SystemSetting, Long> {
    Optional<SystemSetting> findBySettingKeyAndIsActiveTrue(String settingKey);
}
