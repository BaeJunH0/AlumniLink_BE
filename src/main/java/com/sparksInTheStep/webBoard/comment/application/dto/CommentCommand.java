package com.sparksInTheStep.webBoard.comment.application.dto;

import com.sparksInTheStep.webBoard.comment.presentation.dto.CommentRequest;

public record CommentCommand(String Body, Long postId) {
    public static CommentCommand from(CommentRequest.Create commentRequest){
        return new CommentCommand(commentRequest.body(), commentRequest.postId());
    }
}
