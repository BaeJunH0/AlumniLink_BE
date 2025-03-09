package com.sparksInTheStep.webBoard.comment.presentation;

import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.comment.application.CommentService;
import com.sparksInTheStep.webBoard.comment.application.dto.CommentCommand;
import com.sparksInTheStep.webBoard.comment.presentation.dto.CommentRequest;
import com.sparksInTheStep.webBoard.comment.presentation.dto.CommentResponse;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController implements CommentApiSpec{
    private final CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<?> getComment(
            @PageableDefault Pageable pageable,
            @PathVariable Long postId
    ) {
        return new ResponseEntity<>(
                commentService.readCommentsByPostId(postId, pageable).map(CommentResponse::from),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<?> postComment(
            @AuthorizedUser MemberInfo memberInfo,
            @RequestBody CommentRequest commentRequest
    ) {
        commentService.makeNewComment(
                CommentCommand.from(commentRequest),
                memberInfo.nickname()
        );

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<?> updateComment(
            @AuthorizedUser MemberInfo memberInfo,
            @RequestBody CommentRequest commentRequest,
            @PathVariable Long commentId
    ){
        commentService.updateComment(
                memberInfo.nickname(),
                commentId,
                CommentCommand.from(commentRequest)
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @AuthorizedUser MemberInfo memberInfo,
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(memberInfo.nickname(), commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
