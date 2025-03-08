package com.sparksInTheStep.webBoard.project.application;

import com.sparksInTheStep.webBoard.global.errorHandling.CustomException;
import com.sparksInTheStep.webBoard.global.errorHandling.errorCode.ProjectErrorCode;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectMemberRequestInfo;
import com.sparksInTheStep.webBoard.project.doamin.ProjectMember;
import com.sparksInTheStep.webBoard.joinRequest.domain.JoinRequest;
import com.sparksInTheStep.webBoard.project.persistent.ProjectMemberRepository;
import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.member.persistent.MemberRepository;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectCommand;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectInfo;
import com.sparksInTheStep.webBoard.project.doamin.Project;
import com.sparksInTheStep.webBoard.joinRequest.persistent.JoinRequestRepository;
import com.sparksInTheStep.webBoard.project.persistent.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final JoinRequestRepository joinRequestRepository;

    @Transactional(readOnly = true)
    public Page<ProjectInfo> getAllProjects(Pageable pageable){
        LocalDate today = LocalDate.now();
        List<Project> projects = projectRepository.findAll(pageable).stream()
                .filter(project -> {
                    LocalDate deadLine = Instant.ofEpochMilli(project.getDeadline().getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return !deadLine.isAfter(today);
                })
                .toList();
        return new PageImpl<>(projects, pageable, projects.size()).map(ProjectInfo::of);
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
        Page<ProjectMember> joinedProjects = projectMemberRepository.findJoinedProjectsByMember(pageable, member);
        return joinedProjects.map(ProjectMember::getProject).map(ProjectInfo::of);
    }

    @Transactional
    public void makeProject(ProjectCommand projectCommand){
        if(projectRepository.existsByName(projectCommand.name())) {
            throw CustomException.of(ProjectErrorCode.ALREADY_EXIST_NAME);
        }

        if(projectCommand.maxCount() <= 0) {
            throw CustomException.of(ProjectErrorCode.LOGICAL_TEAM_SIZE_ERROR);
        }

        // 프로젝트 생성
        Project project = Project.of(projectCommand);
        projectRepository.save(project);

        // 프로젝트 주인 조회
        Member member = memberRepository.findByNickname(projectCommand.nickname());

        // 주인을 프로젝트의 첫 번째 멤버로 가입
        project.join();
        ProjectMember projectMember = ProjectMember.from(member, project);
        projectMemberRepository.save(projectMember);
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

        if(!projectMemberRepository.existsByMemberAndProject(member, project)){
            throw CustomException.of(ProjectErrorCode.NOT_FOUND);
        }

        project.withdraw();
        if(project.getNowCount() == 0) {
            projectRepository.delete(project);
        }

        projectMemberRepository.deleteByMemberAndProject(member, project);
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
        if(projectMemberRepository.existsByMemberAndProject(member, project)){
            throw CustomException.of(ProjectErrorCode.ALREADY_JOINED_PROJECT);
        }

        JoinRequest joinRequest = JoinRequest.from(project, member);
        joinRequestRepository.save(joinRequest);
    }

    @Transactional(readOnly = true)
    public List<ProjectMemberRequestInfo> readProjectJoinRequest(String nickname) {
        List<JoinRequest> requests =
                joinRequestRepository.findProjectMemberRequestByMember_Nickname(nickname);
        return requests.stream().map(ProjectMemberRequestInfo::of).toList();
    }

    @Transactional
    public void choice(String nickname, Long requestId, boolean tf) {
        JoinRequest request = joinRequestRepository.findById(requestId).
                orElseThrow(() -> CustomException.of(ProjectErrorCode.NO_SUCH_REQUEST));

        if(!request.getProject().getLeaderName().equals(nickname)) {
            throw CustomException.of(ProjectErrorCode.NOT_TEAM_LEADER);
        }

        if(tf) {
            Member member = request.getMember();
            Project project = request.getProject();

            project.join();
            ProjectMember projectMember = ProjectMember.from(member, project);
            projectMemberRepository.save(projectMember);
        }

        joinRequestRepository.delete(request);
    }
}
