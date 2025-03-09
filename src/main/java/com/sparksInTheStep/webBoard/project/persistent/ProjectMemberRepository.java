package com.sparksInTheStep.webBoard.project.persistent;

import com.sparksInTheStep.webBoard.project.doamin.ProjectMember;
import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.project.doamin.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    Page<ProjectMember> findJoinedProjectsByMember(Pageable pageable, Member member);

    Boolean existsByMemberAndProject(Member member, Project project);

    void deleteByMemberAndProject(Member member, Project project);
}
