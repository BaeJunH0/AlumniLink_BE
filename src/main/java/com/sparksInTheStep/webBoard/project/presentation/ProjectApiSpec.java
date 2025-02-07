package com.sparksInTheStep.webBoard.project.presentation;

import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.project.presentation.dto.Choice;
import com.sparksInTheStep.webBoard.project.presentation.dto.ProjectRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "프로젝트 API", description = "프로젝트 기능에 대한 API")
public interface ProjectApiSpec {
    @Operation(summary = "모든 프로젝트 조회", description = "모든 프로젝트를 조회합니다")
    @ApiResponse(responseCode="200", description = "성공")
    ResponseEntity<?> readAllProject(
            @PageableDefault Pageable pageable
    );
    @Operation(summary = "특정 프로젝트 조회", description = "특정 프로젝트를 조회합니다")
    @ApiResponse(responseCode="200", description = "성공")
    ResponseEntity<?> readOneProject(
            @PathVariable Long projectId
    );
    @Operation(summary = "프로젝트 생성", description = "프로젝트를 생성합니다")
    @ApiResponse(responseCode="201", description = "성공")
    ResponseEntity<?> createProject(
            @RequestBody ProjectRequest projectRequest,
            @AuthorizedUser MemberInfo.Default memberInfo
    );
    @Operation(summary = "프로젝트 수정", description = "프로젝트의 내용을 수정합니다 ( 프로젝트 장만 가능 )")
    @ApiResponse(responseCode="200", description = "성공")
    ResponseEntity<?> updateProject(
            @PathVariable Long projectId,
            @RequestBody ProjectRequest projectRequest,
            @AuthorizedUser MemberInfo.Default memberInfo
    );
    @Operation(summary = "프로젝트 삭제", description = "프로젝트를 삭제합니다 ( 프로젝트 장만 가능 )")
    @ApiResponse(responseCode="204", description = "성공")
    ResponseEntity<?> deleteProject(
            @PathVariable Long projectId,
            @AuthorizedUser MemberInfo.Default memberInfo
    );
    @Operation(summary = "내가 가입된 프로젝트 조회", description = "가입한 프로젝트를 조회합니다")
    @ApiResponse(responseCode="200", description = "성공")
    ResponseEntity<?> readMyProject(
            @PageableDefault Pageable pageable,
            @AuthorizedUser MemberInfo.Default memberInfo
    );
    @Operation(summary = "프로젝트 가입요청", description = "프로젝트에 가입을 요청합니다")
    @ApiResponse(responseCode="200", description = "성공")
    ResponseEntity<?> joinToProject(
            @PathVariable Long projectId,
            @AuthorizedUser MemberInfo.Default memberInfo
    );
    @Operation(summary = "프로젝트 탈퇴", description = "프로젝트에서 탈퇴합니다")
    @ApiResponse(responseCode="204", description = "성공")
    ResponseEntity<?> withdrawProject(
            @PathVariable Long projectId,
            @AuthorizedUser MemberInfo.Default memberInfo
    );
    @Operation(summary = "프로젝트 가입 요청 조회", description = "내가 리더인 프로젝트에 대한 요청을 조회합니다")
    @ApiResponse(responseCode="200", description = "성공")
    ResponseEntity<?> readMyProjectRequest(
            @AuthorizedUser MemberInfo.Default memberInfo
    );
    @Operation(summary = "프로젝트 가입 결정", description = "내가 리더인 프로젝트에 대한 요청을 승인 혹은 거절합니다")
    @ApiResponse(responseCode="200", description = "성공")
    ResponseEntity<?> choiceRequest(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @RequestBody Choice tf,
            @PathVariable Long requestId
    );
}
