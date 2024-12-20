package com.sparksInTheStep.webBoard.post.controller;

import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.post.controller.dto.PostRequest;
import com.sparksInTheStep.webBoard.post.controller.dto.PostResponse;
import com.sparksInTheStep.webBoard.post.service.PostService;
import com.sparksInTheStep.webBoard.post.service.dto.PostCommand;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController implements PostApiSpec{
    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> savePost(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @Valid @RequestBody PostRequest request
    ) {
        postService.createPost(memberInfo.nickname(), PostCommand.from(request));
        return ResponseEntity.ok("성공적으로 게시글이 등록되었습니다.");
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

    @DeleteMapping()
    public ResponseEntity<String> deletePost(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @NotNull @RequestBody Long postId
    ) throws AuthenticationException {
        postService.deletePost(memberInfo, postId);

        return ResponseEntity.ok("성공적으로 게시글이 삭제되었습니다.");
    }

    // 포스트 보기 ( 글 읽기는 로그인 필요 x )

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
}
