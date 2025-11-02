package com.bizbridge.backend.auth.utils;

import com.bizbridge.backend.common.entity.User;
import com.bizbridge.backend.admin.entity.Admin;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key; // ✅ add this
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    // ------------------------------
    // Buyer / Seller token generator
    // ------------------------------
    public String generateToken(User user) {
        long expiration = 1000 * 60 * 60 * 24; // 24 hours
        return Jwts.builder()
                .setSubject(user.getMobileNo())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // ------------------------------
    // Admin token generator
    // ------------------------------
    public String generateAdminToken(Admin admin) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        long expiration = 1000 * 60 * 60 * 12; // 12 hours

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
}
