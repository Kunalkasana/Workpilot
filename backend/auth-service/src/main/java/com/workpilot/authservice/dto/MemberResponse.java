package com.workpilot.authservice.dto;

import com.workpilot.authservice.entity.WorkspaceRole;

import java.util.UUID;

public class MemberResponse {

    private UUID userId;
    private String fullName;
    private String email;
    private WorkspaceRole role;

    public MemberResponse() {
    }

    public MemberResponse(UUID userId, String fullName, String email, WorkspaceRole role) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public WorkspaceRole getRole() {
        return role;
    }
}