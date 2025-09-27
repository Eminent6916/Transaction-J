package com.UserService.Utils;

import com.UserService.Dto.Response.JwtData;
import com.UserService.Model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(JwtData payload) {
        return Jwts.builder()
                .setSubject(payload.getEmail()) // subject = email
                .claim("id", payload.getId())
                .claim("status", payload.getStatus())
                .claim("isPinCreated", payload.getIsEmailVerified())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hr
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }
}

