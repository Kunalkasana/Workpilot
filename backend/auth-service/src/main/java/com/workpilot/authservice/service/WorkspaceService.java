package com.workpilot.authservice.service;

import com.workpilot.authservice.entity.User;
import com.workpilot.authservice.entity.WorkspaceMembership;
import com.workpilot.authservice.dto.WorkspaceResponse;
import java.util.List;
import com.workpilot.authservice.dto.AddMemberRequest;
import com.workpilot.authservice.dto.MemberResponse;
import java.util.UUID;
import com.workpilot.authservice.dto.ChangeRoleRequest;
import java.util.Optional;

public interface WorkspaceService {

    WorkspaceMembership createWorkspace(String name, User owner);
    List<WorkspaceResponse> getMyWorkspaces(User user);
    MemberResponse addMember(UUID workspaceId, AddMemberRequest request, User caller);
    MemberResponse changeMemberRole(UUID workspaceId, UUID targetUserId, ChangeRoleRequest request, User caller);
    Optional<WorkspaceResponse> getDefaultWorkspace(User user);
    WorkspaceResponse getWorkspaceForUser(UUID workspaceId, User user);
}