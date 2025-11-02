package com.bizbridge.backend.admin.controller;

import com.bizbridge.backend.admin.entity.SystemSetting;
import com.bizbridge.backend.admin.service.SystemSettingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/settings")
public class SystemSettingController {

    private final SystemSettingService service;

    public SystemSettingController(SystemSettingService service) {
        this.service = service;
    }

    // ✅ Get all settings
    @GetMapping
    public ResponseEntity<List<SystemSetting>> getAllSettings() {
        return ResponseEntity.ok(service.getAllSettings());
    }

    // ✅ Get setting by key
    @GetMapping("/{key}")
    public ResponseEntity<String> getSettingValue(@PathVariable String key) {
        String value = service.getSettingValue(key);
        if (value == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(value);
    }

    // ✅ Update existing setting
    @PutMapping("/{key}")
    public ResponseEntity<SystemSetting> updateSetting(
            @PathVariable String key,
            @RequestParam String value,
            @RequestParam(defaultValue = "1") Long adminId) {
        return ResponseEntity.ok(service.updateSetting(key, value, adminId));
    }

    // ✅ Add new setting
    @PostMapping
    public ResponseEntity<SystemSetting> addSetting(@RequestBody SystemSetting setting) {
        return ResponseEntity.ok(service.addSetting(setting));
    }
}
