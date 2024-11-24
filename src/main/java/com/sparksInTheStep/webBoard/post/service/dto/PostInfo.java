package com.sparksInTheStep.webBoard.post.service.dto;

import com.sparksInTheStep.webBoard.auth.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.post.domain.Post;
import com.sparksInTheStep.webBoard.post.domain.PostType;

import java.time.LocalDateTime;

public record PostInfo(
        Long id,
        String title,
        String body,
        PostType tag,
        MemberInfo memberInfo,
        LocalDateTime startTime,
        LocalDateTime modifiedTime
        ){
    public static PostInfo from(Post post)
    {
       return new PostInfo(
               post.getId(),
               post.getTitle(),
               post.getBody(),
               post.getTag(),
               MemberInfo.from(post.getMember()),
               post.getCreatedDate(),
               post.getLastModifiedDate()
       );
    }
}
