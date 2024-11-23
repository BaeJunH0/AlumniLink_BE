package com.sparksInTheStep.webBoard.content.controller.dto;

import com.sparksInTheStep.webBoard.content.domain.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record PostResponse(
        Long id,
        String nickname,
        String title,
        String body,
        Enum<?> tag,
        LocalDateTime startTime,
        LocalDateTime modifiedTime
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getMember().getNickname(),
                post.getTitle(),
                post.getBody(),
                post.getTag(),
                post.getCreatedDate(),
                post.getLastModifiedDate()
        );
    }
}
