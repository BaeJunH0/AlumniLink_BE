package com.sparksInTheStep.webBoard.post.application.dto;

import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.post.domain.Post;
import com.sparksInTheStep.webBoard.post.domain.PostType;

import java.time.LocalDateTime;

public record PostInfo(
        Long id,
        String title,
        String body,
        String desscription,
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
               post.getDescription(),
               post.getTag(),
               MemberInfo.of(post.getMember()),
               post.getAtCreated(),
               post.getAtModified()
       );
    }
}
