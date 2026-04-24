package com.example.Start_up_crm_client_service.util;

import com.example.Start_up_crm_client_service.entity.Role;
import com.example.Start_up_crm_client_service.exception.JwtTokenException;
import com.example.Start_up_crm_client_service.exception.JwtTokenExpiredException;
import com.example.Start_up_crm_client_service.exception.JwtTokenParseException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    private SecretKey secretKey;

    @Value("${jwt.secretKey}")
    private String secretKeyString;

    @Value("${jwt.expirationMs}")
    private Long expirationMs;

    // ================= INIT =================
    @PostConstruct
    public void init() {
        if (expirationMs == null || expirationMs <= 0) {
            expirationMs = 3600000L;
        }

        try {
            this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            logger.error("Error initializing JwtTokenUtil: {}", e.getMessage(), e);
            this.secretKey = Keys.hmacShaKeyFor("defaultSecretKeydefaultSecretKey".getBytes(StandardCharsets.UTF_8));
        }
    }

    // ================= USER ID =================
    public Long extractUserId(String token) {
        Object userIdObj = extractAllClaims(token).get("userId");

        if (userIdObj == null) {
            return null;
        }

        return Long.parseLong(userIdObj.toString());
    }

    // ================= ROLES =================
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        Object roles = extractAllClaims(token).get("roles");

        if (roles instanceof List<?> list) {
            return list.stream().map(String::valueOf).toList();
        }

        return Collections.emptyList();
    }

    // ================= USERNAME =================
    public String extractUsername(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (Exception e) {
            throw new JwtTokenException("Failed to extract username: " + e.getMessage(), e);
        }
    }

    // ================= EXPIRATION =================
    public Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception e) {
            throw new JwtTokenException("Failed to extract expiration: " + e.getMessage(), e);
        }
    }

    // ================= GENERIC CLAIM =================
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // ================= CORE CLAIM PARSER =================
    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            throw new JwtTokenExpiredException("JWT token expired");
        } catch (JwtException e) {
            throw new JwtTokenParseException("Invalid JWT token");
        }
    }

    // ================= EXPIRY CHECK =================
    private static final long ALLOWED_CLOCK_SKEW = 5 * 60 * 1000;

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = extractExpiration(token);
            long now = System.currentTimeMillis();
            return expiration.getTime() < (now - ALLOWED_CLOCK_SKEW);
        } catch (Exception e) {
            return true;
        }
    }

    // ================= GENERATE ACCESS TOKEN =================
    public String generateToken(String username, Long userId, Set<Role> roles) {

        String[] roleNames = roles.stream()
                .map(role -> role.getName().name())
                .toArray(String[]::new);

        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)   // ✅ FIXED (was "id")
                .claim("roles", roleNames)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // ================= GENERATE SIMPLE TOKEN =================
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 60 * 1000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // ================= REFRESH TOKEN =================
    public String generateRefreshToken(String username) {

        long refreshExpiration = 7 * 24 * 60 * 60 * 1000L;

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // ================= VALIDATION =================
    public Boolean validateToken(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);

            if (extractedUsername.equals(username) && !isTokenExpired(token)) {
                logger.info("Token validation successful for user: {}", username);
                return true;
            }

            logger.warn("Token validation failed for user: {}", username);
            return false;

        } catch (Exception e) {
            logger.error("Token validation error: {}", e.getMessage());
            return false;
        }
    }

    // ================= RESET PASSWORD CLAIMS =================
    public Claims extractResetPasswordClaims(String token) {
        return extractAllClaims(token);
    }

    // ================= LOGGING =================
    private void logError(String message, Exception e) {
        logger.error("{}: {}", message, e.getMessage(), e);
    }
}