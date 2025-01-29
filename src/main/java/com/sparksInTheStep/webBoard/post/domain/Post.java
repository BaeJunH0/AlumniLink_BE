package com.sparksInTheStep.webBoard.post.domain;

import com.sparksInTheStep.webBoard.global.baseEntity.TimeStamp;
import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.post.application.dto.PostCommand;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Post extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String body;

    @Enumerated(EnumType.STRING)
    private PostType tag;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Post(PostCommand postCommand, Member member) {
        this.title = postCommand.title();
        this.body = postCommand.body();
        this.tag = PostType.valueOf(postCommand.tag());
        this.description = postCommand.description();
        this.member = member;
    }

    public static Post from(PostCommand postCommand, Member member) {
        return new Post(postCommand, member);
    }

    public void update(String title, String tag, String body, String description){
        this.title = title;
        this.tag = PostType.valueOf(tag);
        this.body = body;
        this.description = description;
    }
}

