package com.sparksInTheStep.webBoard.admin.presentation;

import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "어드민 API", description = "어드민 기능을 담은 API")
public interface AdminApiSpec {
    @Operation(summary = "모든 멤버 조회", description = "저장된 모든 멤버를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "403", description = "권한 없음")
    ResponseEntity<?> readAllUsers(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @Parameter(description = "따로 값을 주지 않으면 기본값 : size = 10, page = 0")
            @PageableDefault Pageable pageable
    );

    @Operation(summary = "어드민 권한 부여", description = "특정 멤버에게 어드민 권한을 부여합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "403", description = "권한 없음")
    @ApiResponse(responseCode = "404", description = "userId에 해당하는 유저 없음")
    ResponseEntity<?> makeUserAdmin(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @Parameter(description = "유저의 ID")
            @PathVariable Long userId
    );

    @Operation(summary = "멤버 삭제", description = "특정 멤버를 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "성공")
    @ApiResponse(responseCode = "403", description = "권한 없음")
    @ApiResponse(responseCode = "404", description = "userId에 해당하는 유저 없음")
    public ResponseEntity<?> deleteUser(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @Parameter(description = "유저의 ID")
            @PathVariable Long userId
    );
}
