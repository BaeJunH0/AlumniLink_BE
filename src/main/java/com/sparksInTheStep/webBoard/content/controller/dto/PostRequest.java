package com.sparksInTheStep.webBoard.content.controller.dto;

public record PostRequest(
        String title,
        String content,
        String tag
) {

}
