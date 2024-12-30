package com.sparksInTheStep.webBoard.comment.application.dto;

import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.comment.domain.Comment;
import com.sparksInTheStep.webBoard.post.service.dto.PostInfo;

import java.time.LocalDateTime;

public record CommentInfo(
        Long id,
        String body,
        LocalDateTime createdDate,
        MemberInfo.Default memberInfo,
        PostInfo postInfo) {
    public static CommentInfo from(Comment comment){
        return new CommentInfo(
                comment.getId(),
                comment.getBody(),
                comment.getCreatedDate(),
                MemberInfo.Default.from(comment.getMember()),
                PostInfo.from(comment.getPost())
        );
    }
}
