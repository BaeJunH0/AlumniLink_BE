package com.sparksInTheStep.webBoard.post.application;

import com.sparksInTheStep.webBoard.comment.application.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostFacade {
    private final PostService postService;
    private final CommentService commentService;

    public void deletePost(String nickname, Long postId) {
        postService.isOwner(nickname, postId);

        commentService.deletePostComment(postId);
        postService.deletePost(postId);
    }
}
