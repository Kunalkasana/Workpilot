package com.workpilot.authservice.service;

import com.workpilot.authservice.entity.User;
import com.workpilot.authservice.entity.WorkspaceMembership;

public interface WorkspaceService {

    WorkspaceMembership createWorkspace(String name, User owner);
}