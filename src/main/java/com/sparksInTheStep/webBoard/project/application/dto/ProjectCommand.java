package com.sparksInTheStep.webBoard.project.application.dto;

import com.sparksInTheStep.webBoard.project.presentation.dto.ProjectRequest;

public record ProjectCommand(
        String name, String info, String link, String nickname, int maxCount
) {
    public static ProjectCommand from(ProjectRequest projectRequest, String nickname) {
        return new ProjectCommand(
                projectRequest.name(),
                projectRequest.info(),
                projectRequest.link(),
                nickname,
                projectRequest.maxCount()
        );
    }
}
