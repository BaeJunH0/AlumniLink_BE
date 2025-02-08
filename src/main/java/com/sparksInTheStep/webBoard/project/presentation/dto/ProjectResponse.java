package com.sparksInTheStep.webBoard.project.presentation.dto;

import com.sparksInTheStep.webBoard.project.application.dto.ProjectInfo;

import java.util.Date;

public record ProjectResponse(
        Long id,
        String name,
        String info,
        String gitLink,
        String leaderName,
        int nowCount,
        int maxCount,
        Date deadline
) {
    public static ProjectResponse of(ProjectInfo projectInfo) {
        return new ProjectResponse(
                projectInfo.id(),
                projectInfo.name(),
                projectInfo.info(),
                projectInfo.link(),
                projectInfo.leaderName(),
                projectInfo.nowCount(),
                projectInfo.maxCount(),
                projectInfo.deadline()
        );
    }
}
