package com.workpilot.authservice.repository;

import com.workpilot.authservice.entity.WorkspaceMembership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceMembershipRepository extends JpaRepository<WorkspaceMembership, UUID> {

    List<WorkspaceMembership> findAllByUser_Id(UUID userId);

    Optional<WorkspaceMembership> findByUser_IdAndWorkspace_Id(UUID userId, UUID workspaceId);

    boolean existsByUser_IdAndWorkspace_Id(UUID userId, UUID workspaceId);
}