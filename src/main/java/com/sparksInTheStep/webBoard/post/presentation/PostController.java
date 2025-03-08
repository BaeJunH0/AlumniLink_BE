package com.sparksInTheStep.webBoard.post.presentation;

import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.post.application.PostFacade;
import com.sparksInTheStep.webBoard.post.presentation.dto.PostRequest;
import com.sparksInTheStep.webBoard.post.presentation.dto.PostResponse;
import com.sparksInTheStep.webBoard.post.application.PostService;
import com.sparksInTheStep.webBoard.post.application.dto.PostCommand;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController implements PostApiSpec{
    private final PostService postService;
    private final PostFacade postFacade;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(
            @PathVariable Long id
    ){
        PostResponse response = PostResponse.from(postService.getOnePost(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts(
            @PageableDefault Pageable pageable
    ){
        Page<PostResponse> response = postService.getAllPosts(pageable).map(PostResponse::from);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/my")
    public ResponseEntity<?> getPostsByState(
            @PageableDefault Pageable pageable,
            @AuthorizedUser MemberInfo.Default memberInfo
    ) {
        Page<PostResponse> response = postService.getPostsByMember(memberInfo.nickname(), pageable)
                .map(PostResponse::from);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getPostsByUserId(
            @PageableDefault Pageable pageable,
            @PathVariable Long userId
    ) {
        Page<PostResponse> responses = postService.getPostsByMemberId(userId, pageable)
                .map(PostResponse::from);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> savePost(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @RequestBody PostRequest request
    ) {
        postService.createPost(memberInfo.nickname(), PostCommand.from(request));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<?> updatePost(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @RequestBody PostRequest postRequest,
            @PathVariable Long postId
    ){
        postService.updatePost(memberInfo.nickname(), postId, PostCommand.from(postRequest));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @PathVariable Long postId
    ) {
        postFacade.deletePost(memberInfo.nickname(), postId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
