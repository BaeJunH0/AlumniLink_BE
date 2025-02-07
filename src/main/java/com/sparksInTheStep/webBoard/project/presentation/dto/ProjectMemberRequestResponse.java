package com.sparksInTheStep.webBoard.project.presentation.dto;

import com.sparksInTheStep.webBoard.project.application.dto.ProjectMemberRequestInfo;

import java.util.Date;

public record ProjectMemberRequestResponse(
        String projectName,
        String requestMemberName,
        int maxCount,
        int nowCount,
        Date deadline
) {
    public static ProjectMemberRequestResponse of(ProjectMemberRequestInfo requestInfo) {
        return new ProjectMemberRequestResponse(
            requestInfo.projectName(),
            requestInfo.requestMemberName(),
            requestInfo.maxCount(),
            requestInfo.nowCount(),
            requestInfo.deadline()
        );
    };
}
