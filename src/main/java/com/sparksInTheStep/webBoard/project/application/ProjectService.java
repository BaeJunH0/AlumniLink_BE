package com.sparksInTheStep.webBoard.project.application;

import com.sparksInTheStep.webBoard.global.errorHandling.CustomException;
import com.sparksInTheStep.webBoard.global.errorHandling.errorCode.ProjectErrorCode;
import com.sparksInTheStep.webBoard.project.doamin.JoinedProject;
import com.sparksInTheStep.webBoard.project.persistent.JoinedProjectRepository;
import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.member.persistent.MemberRepository;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectCommand;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectInfo;
import com.sparksInTheStep.webBoard.project.doamin.Project;
import com.sparksInTheStep.webBoard.project.persistent.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final JoinedProjectRepository joinedProjectRepository;

    @Transactional(readOnly = true)
    public Page<ProjectInfo> getAllProjects(Pageable pageable){
        return projectRepository.findAll(pageable).map(ProjectInfo::of);
    }

    @Transactional(readOnly = true)
    public ProjectInfo getProject(Long id){
        Project project = projectRepository.findById(id).orElseThrow(
                () -> CustomException.of(ProjectErrorCode.NOT_FOUND)
        );
        return ProjectInfo.of(project);
    }

    @Transactional(readOnly = true)
    public Page<ProjectInfo> getMyProjects(Pageable pageable, String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        Page<JoinedProject> joinedProjects = joinedProjectRepository.findJoinedProjectsByMember(pageable, member);
        return joinedProjects.map(JoinedProject::getProject).map(ProjectInfo::of);
    }

    @Transactional
    public void makeProject(ProjectCommand projectCommand){
        if(projectRepository.existsByName(projectCommand.name())) {
            throw CustomException.of(ProjectErrorCode.ALREADY_EXIST_NAME);
        }

        if(projectCommand.maxCount() <= 0) {
            throw CustomException.of(ProjectErrorCode.LOGICAL_TEAM_SIZE_ERROR);
        }

        Project project = Project.of(projectCommand);
        projectRepository.save(project);
    }

    @Transactional
    public void joinProject(Long id, String nickname) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> CustomException.of(ProjectErrorCode.NOT_FOUND)
        );
        Member member = memberRepository.findByNickname(nickname);

        if(project.getNowCount() == project.getMaxCount()) {
            throw CustomException.of(ProjectErrorCode.OVER_TEAM_SIZE);
        }
        if(joinedProjectRepository.existsByMemberAndProject(member, project)){
            throw CustomException.of(ProjectErrorCode.ALREADY_JOINED_PROJECT);
        }

        project.join();
        JoinedProject joinedProject = JoinedProject.from(member, project);
        joinedProjectRepository.save(joinedProject);
    }

    @Transactional
    public void updateProject(Long id, ProjectCommand projectCommand){
        Project project = projectRepository.findById(id).orElseThrow(
                () -> CustomException.of(ProjectErrorCode.NOT_FOUND)
        );
        if(projectRepository.existsByName(projectCommand.name())) {
            throw CustomException.of(ProjectErrorCode.ALREADY_EXIST_NAME);
        }
        if(!project.getName().equals(projectCommand.nickname())) {
            throw CustomException.of(ProjectErrorCode.NOT_TEAM_LEADER);
        }

        if(projectCommand.maxCount() <= 0) {
            throw CustomException.of(ProjectErrorCode.LOGICAL_TEAM_SIZE_ERROR);
        }
        project.update(
                projectCommand.name(),
                projectCommand.info(),
                projectCommand.link(),
                projectCommand.maxCount()
        );
    }

    @Transactional
    public void deleteProject(Long id, String nickname){
        Project project = projectRepository.findById(id).orElseThrow(
                () -> CustomException.of(ProjectErrorCode.NOT_FOUND)
        );
        if(!project.getName().equals(nickname)) {
            throw CustomException.of(ProjectErrorCode.NOT_TEAM_LEADER);
        }

        projectRepository.delete(project);
    }

    @Transactional
    public void withdrawProject(Long id, String nickname) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> CustomException.of(ProjectErrorCode.NOT_FOUND)
        );
        Member member = memberRepository.findByNickname(nickname);

        if(!joinedProjectRepository.existsByMemberAndProject(member, project)){
            throw CustomException.of(ProjectErrorCode.NOT_FOUND);
        }

        project.withdraw();
        if(project.getNowCount() == 0) {
            projectRepository.delete(project);
        }
        joinedProjectRepository.deleteByMemberAndProject(member, project);
    }
}
