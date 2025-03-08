package com.sparksInTheStep.webBoard.project.application.dto;

import com.sparksInTheStep.webBoard.joinRequest.domain.JoinRequest;

import java.util.Date;

public record ProjectMemberRequestInfo(
        Long id,
        String projectName,
        String requestMemberName,
        int maxCount,
        int nowCount,
        Date deadline
) {
    public static ProjectMemberRequestInfo of(JoinRequest joinRequest) {
        return new ProjectMemberRequestInfo(
                joinRequest.getId(),
                joinRequest.getProject().getName(),
                joinRequest.getMember().getNickname(),
                joinRequest.getProject().getMaxCount(),
                joinRequest.getProject().getNowCount(),
                joinRequest.getProject().getDeadline()
        );
    }
}
