package com.workpilot.authservice.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "workspace_memberships",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "workspace_id"}
                )
        }
)
public class WorkspaceMembership {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private WorkspaceRole role;

    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    protected WorkspaceMembership() {
    }

    public WorkspaceMembership(User user, Workspace workspace,
                               WorkspaceRole role) {
        this.user = user;
        this.workspace = workspace;
        this.role = role;
    }

    @PrePersist
    protected void onCreate() {
        joinedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public WorkspaceRole getRole() {
        return role;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public void setRole(WorkspaceRole role) {
        this.role = role;
    }
}