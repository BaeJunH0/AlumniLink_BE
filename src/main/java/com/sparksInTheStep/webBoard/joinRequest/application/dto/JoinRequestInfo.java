package com.sparksInTheStep.webBoard.joinRequest.application.dto;

import com.sparksInTheStep.webBoard.project.doamin.Project;

import java.util.Date;

public record JoinRequestInfo(
        String projectName,
        String requestMemberName,
        int maxCount,
        int nowCount,
        Date deadline
) {
    public static JoinRequestInfo from(Project project, String requestMemberName) {
        return new JoinRequestInfo(
                project.getName(),
                requestMemberName,
                project.getMaxCount(),
                project.getNowCount(),
                project.getDeadline()
        );
    }
}
