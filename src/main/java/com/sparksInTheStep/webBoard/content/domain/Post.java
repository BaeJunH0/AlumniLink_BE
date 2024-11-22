package com.sparksInTheStep.webBoard.content.domain;

import com.sparksInTheStep.webBoard.auth.domain.Member;
import com.sparksInTheStep.webBoard.content.service.dto.PostCommand;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Post extends TimeStamp{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private PostType tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    public Post(PostCommand postCommand) {
        this.title = postCommand.title();
        this.body = postCommand.body();
        this.tag = PostType.valueOf(postCommand.tag());
    }

}

enum PostType{
    STUDY, FREE, FUTURE
}