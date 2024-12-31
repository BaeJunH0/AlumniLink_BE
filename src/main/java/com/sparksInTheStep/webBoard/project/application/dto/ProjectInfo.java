package com.sparksInTheStep.webBoard.project.application.dto;

import com.sparksInTheStep.webBoard.project.doamin.Project;

public record ProjectInfo(String name, String info, String gitLink, String leaderName) {
    public static ProjectInfo of(Project project){
        return new ProjectInfo(
                project.getName(), project.getInfo(), project.getGitLink(), project.getLeaderName()
        );
    }
}
