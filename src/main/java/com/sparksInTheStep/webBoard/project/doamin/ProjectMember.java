package com.sparksInTheStep.webBoard.project.doamin;

import com.sparksInTheStep.webBoard.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class ProjectMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private ProjectMember(Member member, Project project) {
        this.member = member;
        this.project = project;
    }

    public static ProjectMember from(
            Member member, Project project
    ){
        return new ProjectMember(member, project);
    }
}
