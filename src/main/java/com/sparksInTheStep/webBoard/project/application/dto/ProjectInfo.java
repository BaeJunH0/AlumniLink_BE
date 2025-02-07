package com.sparksInTheStep.webBoard.project.application.dto;

import com.sparksInTheStep.webBoard.project.doamin.Project;

import java.util.Date;

public record ProjectInfo(
        String name,
        String info,
        String link,
        String leaderName,
        int nowCount,
        int maxCount,
        Date deadline
) {
    public static ProjectInfo of(Project project){
        return new ProjectInfo(
                project.getName(),
                project.getInfo(),
                project.getLink(),
                project.getLeaderName(),
                project.getNowCount(),
                project.getMaxCount(),
                project.getDeadline()
        );
    }
}
