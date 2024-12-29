package com.sparksInTheStep.webBoard.post.controller.dto;

import com.sparksInTheStep.webBoard.post.domain.PostType;
import com.sparksInTheStep.webBoard.post.service.dto.PostInfo;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String nickname,
        String title,
        String body,
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
                postInfo.tag(),
                postInfo.startTime(),
                postInfo.modifiedTime()
        );
    }
}
