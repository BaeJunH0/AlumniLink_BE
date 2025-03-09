package com.sparksInTheStep.webBoard.joinRequest.application;

import com.sparksInTheStep.webBoard.global.errorHandling.CustomException;
import com.sparksInTheStep.webBoard.global.errorHandling.errorCode.ProjectErrorCode;
import com.sparksInTheStep.webBoard.joinRequest.application.dto.JoinRequestInfo;
import com.sparksInTheStep.webBoard.member.application.MemberService;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.project.application.ProjectService;
import com.sparksInTheStep.webBoard.project.doamin.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JoinRequestFacade {
    private final JoinRequestService joinRequestService;
    private final ProjectService projectService;
    private final MemberService memberService;

    // 프로젝트에 지원
    public void applyToProject(String memberName, Long projectId) {
        Project project = projectService.findById(projectId).orElseThrow(
                () -> CustomException.of(ProjectErrorCode.NOT_FOUND)
        );
        MemberInfo member = memberService.findMemberByNickname(memberName);

        // 팀 리더인지 확인
        if(project.getLeaderName().equals(memberName)) {
            throw CustomException.of(ProjectErrorCode.ALREADY_JOINED_PROJECT);
        }

        // 팀원인지 확인
        if(projectService.isExistMember(projectId, member.id())) {
            throw CustomException.of(ProjectErrorCode.ALREADY_JOINED_PROJECT);
        }

        // 팀 인원이 다 찼는지 확인
        if(project.getNowCount() == project.getMaxCount()) {
            throw CustomException.of(ProjectErrorCode.OVER_TEAM_SIZE);
        }

        joinRequestService.applyForProject(member.id(), projectId);
    }

    // 내가 리더인 프로젝트에 온 요청을 조회
    public List<Map<Object, Object>> getProjectJoinRequests(String nickname) {
        List<Project> projects = projectService.findByLeaderName(nickname);
        List<Map<Object, Object>> requests = new ArrayList<>();

        for (Project project : projects) {
            requests.add(joinRequestService.getProjectJoinRequest(project.getId()));
        }

        return requests;
    }

    // 내가 보낸 요청들 조회
    public List<JoinRequestInfo> getMemberJoinRequests(Long memberId, String memberName) {
        // 프로젝트 id List가 반환
        Set<Object> memberJoinRequest = joinRequestService.getMemberJoinRequest(memberId);
        List<JoinRequestInfo> joinRequestInfos = new ArrayList<>();

        for (Object o : memberJoinRequest) {
            Project project = projectService.findById((long)o).orElseThrow(
                    () -> CustomException.of(ProjectErrorCode.NOT_FOUND)
            );

            joinRequestInfos.add(JoinRequestInfo.from(project, memberName));
        }
        return joinRequestInfos;
    }

    // 요청 허용 or 거절
    public void approveOrDeny(String leaderName, Long projectId, String memberName, boolean tf) {
        Project project = projectService.findById(projectId).orElseThrow(
                () -> CustomException.of(ProjectErrorCode.NOT_FOUND)
        );

        // 허용하는 주체가 프로젝트 리더인지 검증
        if(!project.getLeaderName().equals(leaderName)) {
            throw CustomException.of(ProjectErrorCode.NOT_TEAM_LEADER);
        }

        Long memberId = memberService.findMemberByNickname(memberName).id();
        joinRequestService.removeRequest(memberId, projectId);

        if(tf) {
            projectService.joinMember(projectId, memberId);
        }
    }
}
