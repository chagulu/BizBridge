package com.bizbridge.backend.admin.service;

import com.bizbridge.backend.admin.entity.SystemSetting;
import com.bizbridge.backend.admin.repository.SystemSettingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemSettingService {

    private final SystemSettingRepository repository;

    public SystemSettingService(SystemSettingRepository repository) {
        this.repository = repository;
    }

    // Get all active settings
    public List<SystemSetting> getAllSettings() {
        return repository.findAll();
    }

    // Get a single setting by key
    public String getSettingValue(String key) {
        return repository.findBySettingKeyAndIsActiveTrue(key)
                .map(SystemSetting::getSettingValue)
                .orElse(null);
    }

    // Update setting value
    public SystemSetting updateSetting(String key, String newValue, Long adminId) {
        SystemSetting setting = repository.findBySettingKeyAndIsActiveTrue(key)
                .orElseThrow(() -> new RuntimeException("Setting not found: " + key));

        setting.setSettingValue(newValue);
        setting.setUpdatedBy(adminId);
        return repository.save(setting);
    }

    // Add new setting
    public SystemSetting addSetting(SystemSetting setting) {
        return repository.save(setting);
    }
}
