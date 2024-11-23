package com.sparksInTheStep.webBoard.content.service.dto;

import com.sparksInTheStep.webBoard.content.controller.dto.PostRequest;
import com.sparksInTheStep.webBoard.content.domain.Post;

public record PostCommand (
        String title,
        String body,
        String tag
){
    public static PostCommand from(PostRequest postRequest) {
        return new PostCommand(postRequest.title(), postRequest.body(), postRequest.tag());
    }
}
