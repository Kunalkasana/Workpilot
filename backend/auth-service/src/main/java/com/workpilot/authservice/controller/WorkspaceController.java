package com.workpilot.authservice.controller;

import com.workpilot.authservice.dto.CreateWorkspaceRequest;
import com.workpilot.authservice.dto.WorkspaceResponse;
import com.workpilot.authservice.entity.User;
import com.workpilot.authservice.entity.WorkspaceMembership;
import com.workpilot.authservice.service.WorkspaceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;import com.workpilot.authservice.dto.AddMemberRequest;
import com.workpilot.authservice.dto.MemberResponse;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;


@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @PostMapping
    public ResponseEntity<WorkspaceResponse> createWorkspace(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreateWorkspaceRequest request) {

        // No valid token means no logged-in user.
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        WorkspaceMembership membership =
                workspaceService.createWorkspace(request.getName(), user);

        WorkspaceResponse response = new WorkspaceResponse(
                membership.getWorkspace().getId(),
                membership.getWorkspace().getName(),
                membership.getWorkspace().getSlug(),
                membership.getRole()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<List<WorkspaceResponse>> getMyWorkspaces(
            @AuthenticationPrincipal User user) {

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(workspaceService.getMyWorkspaces(user));
    }
    @PostMapping("/{workspaceId}/members")
    public ResponseEntity<MemberResponse> addMember(
            @PathVariable UUID workspaceId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody AddMemberRequest request) {

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        MemberResponse response =
                workspaceService.addMember(workspaceId, request, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}