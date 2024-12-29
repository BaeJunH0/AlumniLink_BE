package com.sparksInTheStep.webBoard.comment.domain;

import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String body;
    @CreatedDate
    private LocalDateTime createdAt; // 생성 시간
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
}
