package com.sparksInTheStep.webBoard.comment.presentation;

import com.sparksInTheStep.webBoard.auth.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.comment.application.CommentService;
import com.sparksInTheStep.webBoard.comment.application.dto.CommentCommand;
import com.sparksInTheStep.webBoard.comment.presentation.dto.CommentRequest;
import com.sparksInTheStep.webBoard.comment.presentation.dto.CommentResponse;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<?> getComment(
            @RequestBody CommentRequest.Find postParam,
            @PageableDefault Pageable pageable
    ) {
        return new ResponseEntity<>(
                commentService.readCommentsByPostId(postParam.postId(), pageable)
                        .map(CommentResponse::from),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<?> postComment(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @RequestBody CommentRequest.Create commentRequest
    ) {
        commentService.makeNewComment(
                CommentCommand.from(commentRequest),
                memberInfo.nickname()
        );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @PathVariable Long commentId
    ) throws AuthenticationException {
        commentService.deleteComment(memberInfo.nickname(), commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
