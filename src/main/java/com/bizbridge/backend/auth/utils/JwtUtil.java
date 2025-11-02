package com.bizbridge.backend.auth.utils;

import com.bizbridge.backend.common.entity.User;
import com.bizbridge.backend.admin.entity.Admin;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    // Utility to generate Key from Secret
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // ------------------------------
    // Buyer / Seller Token Generator
    // ------------------------------
    public String generateToken(User user) {
        long expiration = 1000 * 60 * 60 * 24; // 24 hours
        Key key = getSigningKey();

        return Jwts.builder()
                .setSubject(user.getMobileNo())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // ------------------------------
    // Admin Token Generator
    // ------------------------------
    public String generateAdminToken(Admin admin) {
        long expiration = 1000 * 60 * 60 * 12; // 12 hours
        Key key = getSigningKey();

        return Jwts.builder()
                .setSubject(admin.getUsername())              // Identify by username
                .claim("role", admin.getRole().name())         // SUPER_ADMIN / SUB_ADMIN
                .claim("email", admin.getEmail())
                .claim("twoFactorEnabled", admin.isTwoFactorEnabled())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // ------------------------------
    // Validate & Extract Claims
    // ------------------------------
    public Claims validateTokenAndGetClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired", e);
        } catch (JwtException e) {
            throw new RuntimeException("Invalid token", e);
        }
    }

    // ------------------------------
    // Check if token expired
    // ------------------------------
    public boolean isTokenExpired(String token) {
        try {
            Date expiration = validateTokenAndGetClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }

    // ------------------------------
    // Extract Username (Subject)
    // ------------------------------
    public String extractUsername(String token) {
        return validateTokenAndGetClaims(token).getSubject();
    }

    // ------------------------------
    // Extract Role
    // ------------------------------
    public String extractRole(String token) {
        Claims claims = validateTokenAndGetClaims(token);
        return claims.get("role", String.class);
    }
}
