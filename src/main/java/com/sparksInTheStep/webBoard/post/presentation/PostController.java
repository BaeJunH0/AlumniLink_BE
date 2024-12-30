package com.sparksInTheStep.webBoard.post.presentation;

import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.post.presentation.dto.PostRequest;
import com.sparksInTheStep.webBoard.post.presentation.dto.PostResponse;
import com.sparksInTheStep.webBoard.post.application.PostService;
import com.sparksInTheStep.webBoard.post.application.dto.PostCommand;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController implements PostApiSpec{
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        List<PostResponse> response = postService
                .getAllPosts()
                .stream()
                .map(PostResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(
            @PathVariable Long id
    ){
        PostResponse response = PostResponse.from(postService.getOnePost(id));
        return ResponseEntity.ok(response);
    }
    @GetMapping("/my")
    public ResponseEntity<List<PostResponse>> getPostsByMember(
            @AuthorizedUser MemberInfo.Default memberInfo
    ) {
        List<PostResponse> response = postService
                .getPostsByMember(memberInfo)
                .stream()
                .map(PostResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<Void> savePost(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @RequestBody PostRequest request
    ) {
        postService.createPost(memberInfo.nickname(), PostCommand.from(request));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PatchMapping("/{postId}")
    public ResponseEntity<Void> updatePost(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @RequestBody PostRequest postRequest,
            @PathVariable Long postId
    ){
        postService.updatePost(memberInfo, postId, PostCommand.from(postRequest));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @PathVariable Long postId
    ) {
        postService.deletePost(memberInfo, postId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
