package com.sparksInTheStep.webBoard.project.application;

import com.sparksInTheStep.webBoard.global.errorHandling.CustomException;
import com.sparksInTheStep.webBoard.global.errorHandling.errorCode.MemberErrorCode;
import com.sparksInTheStep.webBoard.global.errorHandling.errorCode.ProjectErrorCode;
import com.sparksInTheStep.webBoard.project.doamin.ProjectMember;
import com.sparksInTheStep.webBoard.project.persistent.ProjectMemberRepository;
import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.member.persistent.MemberRepository;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectCommand;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectInfo;
import com.sparksInTheStep.webBoard.project.doamin.Project;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final ProjectMemberRepository projectMemberRepository;

    // 모든 Project 가져오기 ( DeadLine 안지난 Project 만 )
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

    // id로 Project 가져오기
    @Transactional(readOnly = true)
    public ProjectInfo getProject(Long id){
        Project project = projectRepository.findById(id).orElseThrow(
                () -> CustomException.of(ProjectErrorCode.NOT_FOUND)
        );
        return ProjectInfo.of(project);
    }

    // 나의 Project 가져오기
    @Transactional(readOnly = true)
    public Page<ProjectInfo> getMyProjects(Pageable pageable, String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        Page<ProjectMember> joinedProjects = projectMemberRepository.findJoinedProjectsByMember(pageable, member);
        return joinedProjects.map(ProjectMember::getProject).map(ProjectInfo::of);
    }

    // Project 생성
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

    // Project 업데이트
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

    // Project 삭제
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

    // Project 탈퇴
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

    // leader 이름으로 project 조회
    @Transactional
    public List<Project> findByLeaderName(String leaderName) {
        return projectRepository.findByLeaderName(leaderName);
    }

    // id로 project 조회
    @Transactional
    public Optional<Project> findById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    // 가입된 멤버인지 확인
    @Transactional(readOnly = true)
    public boolean isExistMember(Long projectId, Long memberId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> CustomException.of(ProjectErrorCode.NOT_FOUND)
        );
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> CustomException.of(MemberErrorCode.NOT_FOUND)
        );
        return projectMemberRepository.existsByMemberAndProject(member, project);
    }

    // 멤버 가입 로직
    @Transactional
    public void joinMember(Long projectId, Long memberId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> CustomException.of(ProjectErrorCode.NOT_FOUND)
        );
        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> CustomException.of(MemberErrorCode.NOT_FOUND)
        );

        ProjectMember projectMember = ProjectMember.from(member, project);
        projectMemberRepository.save(projectMember);
        project.join();
    }
}
