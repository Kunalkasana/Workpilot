package com.workpilot.authservice.dto;

import com.workpilot.authservice.entity.WorkspaceRole;

import java.util.UUID;

public class WorkspaceResponse {

    private UUID id;
    private String name;
    private String slug;
    private WorkspaceRole role;

    public WorkspaceResponse() {
    }

    public WorkspaceResponse(UUID id, String name, String slug, WorkspaceRole role) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public WorkspaceRole getRole() {
        return role;
    }
}