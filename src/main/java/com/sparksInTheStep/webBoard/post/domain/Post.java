package com.sparksInTheStep.webBoard.post.domain;

import com.sparksInTheStep.webBoard.global.listener.TimeStamp;
import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.post.service.dto.PostCommand;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Post(PostCommand postCommand, Member member) {
        this.title = postCommand.title();
        this.body = postCommand.body();
        this.tag = PostType.valueOf(postCommand.tag());
        this.member = member;
    }

    public void update(String title, String tag, String body){
        this.title = title;
        this.tag = PostType.valueOf(tag);
        this.body = body;
    }
}

