package com.workpilot.authservice.service;

import com.workpilot.authservice.entity.User;
import com.workpilot.authservice.entity.WorkspaceMembership;
import com.workpilot.authservice.dto.WorkspaceResponse;
import java.util.List;
public interface WorkspaceService {

    WorkspaceMembership createWorkspace(String name, User owner);
    List<WorkspaceResponse> getMyWorkspaces(User user);
}