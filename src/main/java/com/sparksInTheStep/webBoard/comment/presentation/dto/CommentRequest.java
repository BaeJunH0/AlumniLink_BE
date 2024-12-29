package com.sparksInTheStep.webBoard.comment.presentation.dto;

public record CommentRequest() {
    public record Create(String body, Long postId){

    }
    public record Find(Long postId){

    }
}
