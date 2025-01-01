package com.sparksInTheStep.webBoard.project.doamin;

import com.sparksInTheStep.webBoard.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class JoinedProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private JoinedProject(Member member, Project project) {
        this.member = member;
        this.project = project;
    }

    public static JoinedProject from(
            Member member, Project project
    ){
        return new JoinedProject(member, project);
    }
}
