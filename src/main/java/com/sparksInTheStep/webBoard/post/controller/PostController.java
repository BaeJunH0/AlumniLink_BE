package com.sparksInTheStep.webBoard.post.controller;

import com.sparksInTheStep.webBoard.auth.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.post.controller.dto.PostRequest;
import com.sparksInTheStep.webBoard.post.controller.dto.PostResponse;
import com.sparksInTheStep.webBoard.post.service.PostService;
import com.sparksInTheStep.webBoard.post.service.dto.PostCommand;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> savePost(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @RequestBody PostRequest request
    ) {
        postService.createPost(memberInfo.nickname(), PostCommand.from(request));
        return ResponseEntity.ok("성공적으로 게시글이 등록되었습니다.");
    }

    @GetMapping("/my")
    public ResponseEntity<List<PostResponse>> getPostsByMember(
            @AuthorizedUser MemberInfo.Default memberInfo
    ) {
        return ResponseEntity.ok(
                postService.getPostsByMember(memberInfo).stream().
                        map(PostResponse::from).
                        toList()
        );
    }

    @DeleteMapping
    public ResponseEntity<String> deletePost(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @RequestBody Long postId
    ) throws AuthenticationException {
        postService.deletePost(memberInfo, postId);

        return ResponseEntity.ok("성공적으로 게시글이 삭제되었습니다.");
    }

    // 포스트 전체 보기 ( 글 읽기는 로그인 필요 x )
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        return ResponseEntity.ok(
                postService.getAllPosts().
                        stream().
                        map(PostResponse::from).
                        toList()
        );
    }
}
