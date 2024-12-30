package com.sparksInTheStep.webBoard.post.presentation;

import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.post.presentation.dto.PostRequest;
import com.sparksInTheStep.webBoard.post.presentation.dto.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name="게시글 API", description = "게시글 기능을 담은 API")
public interface PostApiSpec {
    @Operation(summary = "모든 게시글 조회하기", description = "모든 게시글을 조회합니다")
    @ApiResponse(responseCode="200", description = "성공")
    public ResponseEntity<List<PostResponse>> getAllPosts();

    @Operation(summary = "특정 게시글 조회하기", description = "특정 게시글을 조회합니다")
    @ApiResponse(responseCode="200", description = "성공")
    @ApiResponse(responseCode = "404", description = "id에 해당하는 게시글이 없습니다")
    public ResponseEntity<PostResponse> getPost(
            @PathVariable Long id
    );

    @Operation(summary = "자기가 쓴 글 불러오기", description = "자기가 쓴 모든 글을 리스트로 반환합니다")
    @ApiResponse(responseCode="200", description = "성공")
    @ApiResponse(responseCode="404", description = "Bearer 토큰이 유효하지 않습니다")
    public ResponseEntity<List<PostResponse>> getPostsByMember(
            @AuthorizedUser MemberInfo.Default memberInfo
    );

    @Operation(summary = "게시글 저장", description = "게시글을 저장합니다.")
    @ApiResponse(responseCode="201", description = "성공")
    @ApiResponse(responseCode="404", description = "Bearer 토큰이 유효하지 않습니다")
    @ApiResponse(responseCode="500", description = "파라미터의 값이 유효하지 않습니다")
    public ResponseEntity<Void> savePost(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @Parameter(description = "제목과 바디 및 태그를 파라미터로 받습니다")
            @RequestBody PostRequest request
    );

    @Operation(summary = "게시글 수정하기", description = "해당 게시글을 수정합니다.")
    @ApiResponse(responseCode="200", description = "성공")
    @ApiResponse(responseCode="404", description = "Bearer 토큰이 유효하지 않습니다")
    @ApiResponse(responseCode="500", description = "게시글 없거나 이미 게시글 번호 유효하지 않음")
    public ResponseEntity<Void> updatePost(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @RequestBody PostRequest postRequest,
            @PathVariable Long postId
    );

    @Operation(summary = "게시글 삭제하기", description = "해당 게시글을 삭제합니다.")
    @ApiResponse(responseCode="204", description = "성공")
    @ApiResponse(responseCode="404", description = "Bearer 토큰이 유효하지 않습니다")
    @ApiResponse(responseCode="500", description = "게시글 없거나 이미 게시글 번호 유효하지 않음")
    public ResponseEntity<Void> deletePost(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @PathVariable Long postId
    );
}
