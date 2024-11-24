package com.sparksInTheStep.webBoard.post.controller.dto;

public record PostRequest(
        String title,
        String body,
        String tag
) {

}
