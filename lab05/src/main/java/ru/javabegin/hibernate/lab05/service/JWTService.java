package ru.javabegin.hibernate.lab05.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JWTService {

    private final Key key = Keys.hmacShaKeyFor("your-very-long-secret-key-your-very-long-secret-key".getBytes());
    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 часа

    // ---------- Создание токена ----------
    public String getToken(Authentication auth) {

        // Достаём роль из Authentication
        String role = auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

        return Jwts.builder()
                .setSubject(auth.getName())
                .claim("role", role) // добавляем роль в JWT
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        Object role = extractAllClaims(token).get("role");
        return role != null ? role.toString() : null;
    }
}
