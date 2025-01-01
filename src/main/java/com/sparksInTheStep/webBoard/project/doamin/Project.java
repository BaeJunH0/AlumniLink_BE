package com.sparksInTheStep.webBoard.project.doamin;

import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.project.application.dto.ProjectCommand;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String info;
    @Column(nullable = false)
    private String gitLink;
    @Column(nullable = false)
    private String leaderName;

    private Project(String name, String info, String gitLink, String leaderName){
        this.name = name;
        this.info = info;
        this.gitLink = gitLink;
        this.leaderName = leaderName;
    }

    public static Project of(ProjectCommand projectCommand){
        return new Project(
                projectCommand.name(),
                projectCommand.info(),
                projectCommand.gitLink(),
                projectCommand.name()
        );
    }

    public void update(String name, String info, String gitLink){
        this.name = name;
        this.info = info;
        this.gitLink = gitLink;
    }
}
