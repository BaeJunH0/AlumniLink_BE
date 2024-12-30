package com.sparksInTheStep.webBoard.comment.domain;

import com.sparksInTheStep.webBoard.global.listener.TimeStamp;
import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Comment extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String body;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    private Comment(String body, Member member, Post post){
        this.body = body;
        this.member = member;
        this.post = post;
    }

    public static Comment of(String body, Member member, Post post){
        return new Comment(body, member, post);
    }

    public void update(String body){
        this.body = body;
    }
}
