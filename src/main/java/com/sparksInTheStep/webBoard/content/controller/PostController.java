package com.sparksInTheStep.webBoard.content.controller;

import com.sparksInTheStep.webBoard.auth.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.content.controller.dto.PostRequest;
import com.sparksInTheStep.webBoard.content.controller.dto.PostResponse;
import com.sparksInTheStep.webBoard.content.service.PostService;
import com.sparksInTheStep.webBoard.content.service.dto.PostCommand;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> savePost(@AuthorizedUser MemberInfo memberInfo, @RequestBody PostRequest request) {
        postService.createPost(memberInfo, PostCommand.from(request));
        return ResponseEntity.ok("성공적으로 게시글이 등록되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(@AuthorizedUser MemberInfo memberInfo) {
        List<PostResponse> posts = postService.getPostsByMember(memberInfo);
        return ResponseEntity.ok(posts);
    }

    @DeleteMapping
    public ResponseEntity<String> deletePost(@AuthorizedUser MemberInfo memberInfo, @RequestBody Long postId) {
        postService.deletePost(memberInfo, postId);

        return ResponseEntity.ok("성공적으로 게시글이 삭제되었습니다.");
    }
}
