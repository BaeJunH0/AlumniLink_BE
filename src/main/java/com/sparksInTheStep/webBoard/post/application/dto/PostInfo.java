package com.sparksInTheStep.webBoard.post.application.dto;

import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.post.domain.Post;
import com.sparksInTheStep.webBoard.post.domain.PostType;

import java.time.LocalDateTime;

public record PostInfo(
        Long id,
        String title,
        String body,
        PostType tag,
        MemberInfo.Default memberInfo,
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
               MemberInfo.Default.from(post.getMember()),
               post.getCreatedDate(),
               post.getLastModifiedDate()
       );
    }
}
