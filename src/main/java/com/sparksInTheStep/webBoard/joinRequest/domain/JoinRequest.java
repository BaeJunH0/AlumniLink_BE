package com.sparksInTheStep.webBoard.joinRequest.domain;

import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.project.doamin.Project;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class JoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private boolean state;

    private JoinRequest(Project project, Member member){
        this.project = project;
        this.member = member;
        this.state = false;
    }

    public static JoinRequest from(Project project, Member member) {
        return new JoinRequest(project, member);
    }
}
