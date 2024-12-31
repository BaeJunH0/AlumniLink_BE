package com.sparksInTheStep.webBoard.post.application.dto;

import com.sparksInTheStep.webBoard.post.presentation.dto.PostRequest;

public record PostCommand (
        String title,
        String body,
        String tag
){
    public static PostCommand from(PostRequest postRequest) {
        return new PostCommand(postRequest.title(), postRequest.body(), postRequest.tag());
    }
}
