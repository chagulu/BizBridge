package com.bizbridge.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 🔓 Public endpoints
                        .requestMatchers(
                                "/api/auth/**",       // Buyer & Seller OTP login
                                "/api/admin/login",   // Super Admin login
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // 🔒 Everything else requires authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .sessionManagement(session -> session.disable());

        return http.build();
    }
}
