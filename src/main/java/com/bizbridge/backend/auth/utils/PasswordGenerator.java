package com.bizbridge.backend.auth.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {
        String rawPassword = "Super@12345"; // 👈 Change this to your password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("BCrypt Hash for '" + rawPassword + "':");
        System.out.println(encodedPassword);
    }
}
