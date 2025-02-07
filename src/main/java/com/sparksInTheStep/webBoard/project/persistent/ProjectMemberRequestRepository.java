package com.sparksInTheStep.webBoard.project.persistent;

import com.sparksInTheStep.webBoard.project.doamin.ProjectMemberRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRequestRepository extends JpaRepository<ProjectMemberRequest, Long> {
    List<ProjectMemberRequest> findProjectMemberRequestByMember_Nickname(String nickname);
}
