package com.sparksInTheStep.webBoard.post.controller.dto;


import jakarta.validation.constraints.NotBlank;

public record PostRequest(
        @NotBlank
        String title,
        @NotBlank
        String body,
        String tag
) {

}
