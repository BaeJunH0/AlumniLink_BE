package com.sparksInTheStep.webBoard.comment.presentation.dto;

import com.sparksInTheStep.webBoard.member.presentation.dto.MemberResponse;
import com.sparksInTheStep.webBoard.comment.application.dto.CommentInfo;
import com.sparksInTheStep.webBoard.post.presentation.dto.PostResponse;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String body,
        LocalDateTime createdAt,
        String author
) {
    public static CommentResponse from(CommentInfo commentInfo){
        return new CommentResponse(
                commentInfo.id(),
                commentInfo.body(),
                commentInfo.createdDate(),
                commentInfo.author()
        );
    }
}
