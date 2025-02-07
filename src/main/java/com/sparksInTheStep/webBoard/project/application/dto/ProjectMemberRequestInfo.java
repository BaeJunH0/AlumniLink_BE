package com.sparksInTheStep.webBoard.project.application.dto;

import com.sparksInTheStep.webBoard.project.doamin.ProjectMemberRequest;

import java.util.Date;

public record ProjectMemberRequestInfo(
        String projectName,
        String requestMemberName,
        int maxCount,
        int nowCount,
        Date deadline
) {
    public static ProjectMemberRequestInfo of(ProjectMemberRequest projectMemberRequest) {
        return new ProjectMemberRequestInfo(
                projectMemberRequest.getProject().getName(),
                projectMemberRequest.getMember().getNickname(),
                projectMemberRequest.getProject().getMaxCount(),
                projectMemberRequest.getProject().getNowCount(),
                projectMemberRequest.getProject().getDeadline()
        );
    }
}
