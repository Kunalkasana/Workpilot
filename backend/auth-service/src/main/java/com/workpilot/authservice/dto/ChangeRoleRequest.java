package com.workpilot.authservice.dto;

import com.workpilot.authservice.entity.WorkspaceRole;
import jakarta.validation.constraints.NotNull;

public class ChangeRoleRequest {

    @NotNull(message = "Role is required")
    private WorkspaceRole role;

    public WorkspaceRole getRole() {
        return role;
    }

    public void setRole(WorkspaceRole role) {
        this.role = role;
    }
}