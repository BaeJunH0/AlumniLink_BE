package com.sparksInTheStep.webBoard.comment.presentation.dto;

import com.sparksInTheStep.webBoard.member.presentation.dto.MemberResponse;
import com.sparksInTheStep.webBoard.comment.application.dto.CommentInfo;
import com.sparksInTheStep.webBoard.post.controller.dto.PostResponse;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String body,
        LocalDateTime createdAt,
        MemberResponse.Default memberResponse,
        PostResponse postResponse
) {
    public static CommentResponse from(CommentInfo commentInfo){
        return new CommentResponse(
                commentInfo.id(),
                commentInfo.body(),
                commentInfo.createdAt(),
                MemberResponse.Default.from(commentInfo.memberInfo()),
                PostResponse.from(commentInfo.postInfo())
        );
    }
}
