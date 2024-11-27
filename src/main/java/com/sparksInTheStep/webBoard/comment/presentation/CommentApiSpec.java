package com.sparksInTheStep.webBoard.comment.presentation;

import com.sparksInTheStep.webBoard.comment.presentation.dto.CommentRequest;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "댓글 API", description = "댓글 기능에 대한 API")
public interface CommentApiSpec {
    @Operation(summary = "댓글 조회하기", description = "특정 게시글의 댓글을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "404", description = "해당 게시글이 존재하지 않음")
    public ResponseEntity<?> getComment(
            @Parameter(description = "댓글을 찾기 위한 정보")
            @RequestBody CommentRequest.Find postParam,
            @Parameter(description = "따로 값을 주지 않으면 기본값 : size = 10, page = 0")
            @PageableDefault Pageable pageable
    );

    @Operation(summary = "댓글 작성하기", description = "특정 게시글에 댓글을 작성합니다.")
    @ApiResponse(responseCode = "201", description = "성공")
    @ApiResponse(responseCode = "400", description = "유효하지 않은 토큰")
    @ApiResponse(responseCode = "404", description = "해당 게시글이 존재하지 않음 or 위조된 토큰 사용")
    public ResponseEntity<?> postComment(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @Parameter(description = "댓글 생성을 위한 정보")
            @RequestBody CommentRequest.Create commentRequest
    );

    @Operation(summary = "댓글 삭제하기", description = "특정 게시글의 댓글을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "성공")
    @ApiResponse(responseCode = "400", description = "유효하지 않은 토큰")
    @ApiResponse(responseCode = "403", description = "로그인 정보와 일치하지 않는 댓글을 삭제 시도")
    @ApiResponse(responseCode = "404", description = "해당 댓글이 존재하지 않음 or 위조된 토큰 사용")
    public ResponseEntity<?> deleteComment(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @Parameter(description = "삭제할 댓글의 ID")
            @PathVariable Long commentId
    );
}
