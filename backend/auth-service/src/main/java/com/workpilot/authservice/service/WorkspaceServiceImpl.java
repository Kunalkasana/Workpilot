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
import com.workpilot.authservice.dto.AddMemberRequest;
import com.workpilot.authservice.dto.MemberResponse;
import com.workpilot.authservice.exception.InsufficientPermissionException;
import com.workpilot.authservice.exception.MemberAlreadyExistsException;
import com.workpilot.authservice.exception.UserNotFoundException;
import com.workpilot.authservice.exception.WorkspaceNotFoundException;
import com.workpilot.authservice.repository.UserRepository;
import java.util.UUID;
import com.workpilot.authservice.dto.ChangeRoleRequest;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMembershipRepository membershipRepository;
    private final UserRepository userRepository;

    public WorkspaceServiceImpl(
            WorkspaceRepository workspaceRepository,
            WorkspaceMembershipRepository membershipRepository,
            UserRepository userRepository) {

        this.workspaceRepository = workspaceRepository;
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
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
    @Override
    @Transactional
    public MemberResponse addMember(UUID workspaceId, AddMemberRequest request, User caller) {

        // Not a member (or no such workspace): behave the same, reveal nothing.
        WorkspaceMembership callerMembership = membershipRepository
                .findByUser_IdAndWorkspace_Id(caller.getId(), workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException("Workspace not found"));

        WorkspaceRole callerRole = callerMembership.getRole();
        WorkspaceRole newRole = request.getRole();

        if (callerRole != WorkspaceRole.OWNER && callerRole != WorkspaceRole.ADMIN) {
            throw new InsufficientPermissionException(
                    "Only owners and admins can add members");
        }
        if (newRole == WorkspaceRole.OWNER) {
            throw new InsufficientPermissionException(
                    "The owner role cannot be assigned");
        }
        if (newRole == WorkspaceRole.ADMIN && callerRole != WorkspaceRole.OWNER) {
            throw new InsufficientPermissionException(
                    "Only the owner can add admins");
        }

        User newMember = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(
                        "No registered user with that email"));

        if (membershipRepository.existsByUser_IdAndWorkspace_Id(
                newMember.getId(), workspaceId)) {
            throw new MemberAlreadyExistsException(
                    "User is already a member of this workspace");
        }

        WorkspaceMembership saved = membershipRepository.save(
                new WorkspaceMembership(
                        newMember, callerMembership.getWorkspace(), newRole));

        return new MemberResponse(
                newMember.getId(),
                newMember.getFullName(),
                newMember.getEmail(),
                saved.getRole());
    }
    @Override
    @Transactional
    public MemberResponse changeMemberRole(
            UUID workspaceId, UUID targetUserId, ChangeRoleRequest request, User caller) {

        WorkspaceMembership callerMembership = membershipRepository
                .findByUser_IdAndWorkspace_Id(caller.getId(), workspaceId)
                .orElseThrow(() -> new WorkspaceNotFoundException("Workspace not found"));

        if (callerMembership.getRole() != WorkspaceRole.OWNER) {
            throw new InsufficientPermissionException(
                    "Only the owner can change roles");
        }
        if (request.getRole() == WorkspaceRole.OWNER) {
            throw new InsufficientPermissionException(
                    "The owner role cannot be assigned");
        }

        WorkspaceMembership target = membershipRepository
                .findByUser_IdAndWorkspace_Id(targetUserId, workspaceId)
                .orElseThrow(() -> new UserNotFoundException(
                        "User is not a member of this workspace"));

        if (target.getRole() == WorkspaceRole.OWNER) {
            throw new InsufficientPermissionException(
                    "The owner's role cannot be changed");
        }

        target.setRole(request.getRole());
        WorkspaceMembership saved = membershipRepository.save(target);

        User member = saved.getUser();

        return new MemberResponse(
                member.getId(),
                member.getFullName(),
                member.getEmail(),
                saved.getRole());
    }
}