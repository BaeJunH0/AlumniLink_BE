package com.sparksInTheStep.webBoard.project.application.dto;

import com.sparksInTheStep.webBoard.project.presentation.dto.ProjectRequest;

import java.util.Date;

public record ProjectCommand(
        String name, String info, String link, String nickname, int maxCount, Date deadline
) {
    public static ProjectCommand from(ProjectRequest projectRequest, String nickname) {
        return new ProjectCommand(
                projectRequest.name(),
                projectRequest.info(),
                projectRequest.link(),
                nickname,
                projectRequest.maxCount(),
                projectRequest.deadline()
        );
    }
}
