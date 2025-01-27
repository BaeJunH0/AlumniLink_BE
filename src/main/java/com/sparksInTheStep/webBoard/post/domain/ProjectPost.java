package com.sparksInTheStep.webBoard.post.domain;

import com.sparksInTheStep.webBoard.project.doamin.Project;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
public class ProjectPost extends Post {
    @Column(nullable = false)
    private Date deadline;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}
