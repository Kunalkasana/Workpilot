package com.workpilot.authservice.service;

import com.workpilot.authservice.entity.User;
import com.workpilot.authservice.entity.WorkspaceRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long expirationMs;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long expirationMs) {

        this.signingKey = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );

        this.expirationMs = expirationMs;
    }

    // Token without workspace claims (for users who have no workspace).
    public String generateToken(User user) {
        return generateToken(user, null, null);
    }

    // Token carrying the user's active workspace and role.
    public String generateToken(User user, UUID workspaceId, WorkspaceRole role) {

        Date now = new Date();

        Date expiration = new Date(
                now.getTime() + expirationMs
        );

        JwtBuilder builder = Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .issuedAt(now)
                .expiration(expiration);

        if (workspaceId != null && role != null) {
            builder.claim("workspaceId", workspaceId.toString())
                    .claim("role", role.name());
        }

        return builder.signWith(signingKey).compact();
    }

    // Verify the signature and expiration, then return the claims.
    public Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Check whether a token is valid.
    public boolean isTokenValid(String token) {

        try {
            extractClaims(token);
            return true;

        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    // Extract the user's UUID from the JWT.
    public UUID extractUserId(String token) {

        return UUID.fromString(
                extractClaims(token).getSubject()
        );
    }
}