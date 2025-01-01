package com.sparksInTheStep.webBoard.project.presentation.dto;

import com.sparksInTheStep.webBoard.project.application.dto.ProjectInfo;

public record ProjectResponse(String name, String info, String gitLink, String leaderName) {
    public static ProjectResponse of(ProjectInfo projectInfo) {
        return new ProjectResponse(
                projectInfo.name(),
                projectInfo.info(),
                projectInfo.gitLink(),
                projectInfo.leaderName()
        );
    }
}
