package com.sparksInTheStep.webBoard.joinRequest.presentation.dto;

import com.sparksInTheStep.webBoard.joinRequest.application.dto.JoinRequestInfo;

import java.util.Date;

public record JoinRequestResponse(
        String projectName,
        String requestMemberName,
        int maxCount,
        int nowCount,
        Date deadline
) {
    public static JoinRequestResponse of(JoinRequestInfo joinRequestInfo) {
        return new JoinRequestResponse(
                joinRequestInfo.projectName(),
                joinRequestInfo.requestMemberName(),
                joinRequestInfo.maxCount(),
                joinRequestInfo.nowCount(),
                joinRequestInfo.deadline()
        );
    }
}
