package com.sparksInTheStep.webBoard.post.presentation.dto;

import com.sparksInTheStep.webBoard.post.domain.PostType;
import com.sparksInTheStep.webBoard.post.application.dto.PostInfo;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String nickname,
        String title,
        String body,
        String description,
        PostType tag,
        LocalDateTime startTime,
        LocalDateTime modifiedTime
) {
    public static PostResponse from(PostInfo postInfo) {
        return new PostResponse(
                postInfo.id(),
                postInfo.memberInfo().nickname(),
                postInfo.title(),
                postInfo.body(),
                postInfo.desscription(),
                postInfo.tag(),
                postInfo.startTime(),
                postInfo.modifiedTime()
        );
    }
}
