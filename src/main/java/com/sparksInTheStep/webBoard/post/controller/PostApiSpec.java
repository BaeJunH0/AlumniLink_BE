package com.sparksInTheStep.webBoard.post.controller;

import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.post.controller.dto.PostRequest;
import com.sparksInTheStep.webBoard.post.controller.dto.PostResponse;
import com.sparksInTheStep.webBoard.post.domain.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name="게시글 API", description = "게시글 기능을 담은 API")
public interface PostApiSpec {

    @Operation(summary = "게시글 저장", description = "게시글의 제목, 본문, 태그(INFORMATION, STUDY, QUESTION)를 받아 게시글을 저장합니다.")
    @ApiResponse(responseCode="200", description = "성공")
    @ApiResponse(responseCode="404", description = "Bearer 토큰이 유효하지 않습니다")
    @ApiResponse(responseCode="500", description = "파라미터의 값이 유효하지 않습니다")
    public ResponseEntity<String> savePost(
            @Parameter(description = "제목과 바디 및 태그를 파라미터로 받습니다")
            @RequestBody @AuthorizedUser MemberInfo.Default memberInfo,
            @Valid @RequestBody PostRequest request
    );

    @Operation(summary = "자기가 쓴 글 불러오기", description = "자기가 쓴 모든 글을 리스트로 반환합니다")
    @ApiResponse(responseCode="200", description = "성공")
    @ApiResponse(responseCode="404", description = "Bearer 토큰이 유효하지 않습니다")
    public ResponseEntity<List<PostResponse>> getPostsByMember(
            @Parameter(description = "Bearer 토큰을 받음")
            @RequestBody MemberInfo.Default memberInfo
    );

    @Operation(summary = "게시글 삭제하기", description = "해당 게시글을 삭제합니다.")
    @ApiResponse(responseCode="200", description = "성공")
    @ApiResponse(responseCode="404", description = "Bearer 토큰이 유효하지 않습니다")
    @ApiResponse(responseCode="500", description = "게시글 없거나 이미 게시글 번호 유효하지 않음")
    public ResponseEntity<String> deletePost(
            @Parameter(description = "Bearer 토큰과 삭제할 게시글 Id를 받습니다")
            @RequestBody @AuthorizedUser MemberInfo.Default memberInfo,
            @NotNull @RequestBody Long postId
    ) throws AuthenticationException;


    @Operation(summary = "모든 게시글 조회하기", description = "모든 게시글을 조회합니다")
    @ApiResponse(responseCode="200", description = "성공")
    public ResponseEntity<List<PostResponse>> getAllPosts();
}
