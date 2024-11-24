package com.sparksInTheStep.webBoard.comment.application.dto;

import com.sparksInTheStep.webBoard.auth.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.comment.domain.Comment;
import com.sparksInTheStep.webBoard.post.service.dto.PostInfo;

import java.time.LocalDateTime;

public record CommentInfo(
        Long id,
        String body,
        LocalDateTime createdAt,
        MemberInfo memberInfo,
        PostInfo postInfo) {
    public static CommentInfo from(Comment comment){
        return new CommentInfo(
                comment.getId(),
                comment.getBody(),
                comment.getCreatedAt(),
                MemberInfo.from(comment.getMember()),
                PostInfo.from(comment.getPost())
        );
    }
}
