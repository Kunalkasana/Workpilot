package com.workpilot.authservice.dto;

import java.util.UUID;

public class AuthResponse {

    private UUID userId;
    private String fullName;
    private String email;
    private String accessToken;
    private String tokenType;

    public AuthResponse() {
    }

    public AuthResponse(UUID userId, String fullName, String email,
                        String accessToken, String tokenType) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}