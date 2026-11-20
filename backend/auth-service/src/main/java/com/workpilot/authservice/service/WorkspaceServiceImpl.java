package com.workpilot.authservice.service;

import com.workpilot.authservice.entity.User;
import com.workpilot.authservice.entity.Workspace;
import com.workpilot.authservice.entity.WorkspaceMembership;
import com.workpilot.authservice.entity.WorkspaceRole;
import com.workpilot.authservice.repository.WorkspaceMembershipRepository;
import com.workpilot.authservice.repository.WorkspaceRepository;
import com.workpilot.authservice.util.SlugGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.workpilot.authservice.dto.WorkspaceResponse;
import java.util.Comparator;
import java.util.List;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMembershipRepository membershipRepository;

    public WorkspaceServiceImpl(
            WorkspaceRepository workspaceRepository,
            WorkspaceMembershipRepository membershipRepository) {

        this.workspaceRepository = workspaceRepository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    @Transactional
    public WorkspaceMembership createWorkspace(String name, User owner) {

        String slug = SlugGenerator.uniqueSlug(
                name, workspaceRepository::existsBySlug);

        Workspace workspace = workspaceRepository.save(
                new Workspace(name, slug));

        return membershipRepository.save(
                new WorkspaceMembership(owner, workspace, WorkspaceRole.OWNER));
    }
    @Override
    @Transactional(readOnly = true)
    public List<WorkspaceResponse> getMyWorkspaces(User user) {

        return membershipRepository.findAllByUser_Id(user.getId()).stream()
                .sorted(Comparator.comparing(WorkspaceMembership::getJoinedAt))
                .map(membership -> new WorkspaceResponse(
                        membership.getWorkspace().getId(),
                        membership.getWorkspace().getName(),
                        membership.getWorkspace().getSlug(),
                        membership.getRole()))
                .toList();
    }
}