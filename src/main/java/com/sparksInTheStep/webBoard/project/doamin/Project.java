package com.sparksInTheStep.webBoard.project.doamin;

import com.sparksInTheStep.webBoard.global.baseEntity.TimeStamp;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectCommand;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Project extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String info;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String leaderName;

    @Column(nullable = false)
    private int maxCount;

    @Column(nullable = false)
    private int nowCount;

    @Column(nullable = false)
    private Date deadline;

    private Project(String name, String info, String link, String leaderName, int maxCount, Date deadline){
        this.name = name;
        this.info = info;
        this.link = link;
        this.leaderName = leaderName;
        this.maxCount = maxCount;
        this.nowCount = 0;
        this.deadline = deadline;
    }

    public static Project of(ProjectCommand projectCommand){
        return new Project(
                projectCommand.name(),
                projectCommand.info(),
                projectCommand.link(),
                projectCommand.nickname(),
                projectCommand.maxCount(),
                projectCommand.deadline()
        );
    }

    public void update(String name, String info, String link, int maxCount){
        this.name = name;
        this.info = info;
        this.link = link;
        this.maxCount = maxCount;
    }

    public void join() {
        this.nowCount += 1;
    }

    public void withdraw() {
        this.nowCount -= 1;
    }
}
