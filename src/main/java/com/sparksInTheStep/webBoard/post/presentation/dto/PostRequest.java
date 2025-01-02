package com.sparksInTheStep.webBoard.post.presentation.dto;

public record PostRequest(
        String title,
        String body,
        String tag,
        String description
) {

}
