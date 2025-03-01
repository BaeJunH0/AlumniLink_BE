package com.sparksInTheStep.webBoard.project.application.dto;

import com.sparksInTheStep.webBoard.project.doamin.Project;

import java.time.LocalDateTime;
import java.util.Date;

public record ProjectInfo(
        Long id,
        String name,
        String info,
        String link,
        String leaderName,
        int nowCount,
        int maxCount,
        Date deadline,
        LocalDateTime atCreated,
        LocalDateTime atModified
) {
    public static ProjectInfo of(Project project){
        return new ProjectInfo(
                project.getId(),
                project.getName(),
                project.getInfo(),
                project.getLink(),
                project.getLeaderName(),
                project.getNowCount(),
                project.getMaxCount(),
                project.getDeadline(),
                project.getAtCreated(),
                project.getAtModified()
        );
    }
}
