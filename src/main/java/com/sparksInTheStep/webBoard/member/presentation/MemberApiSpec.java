package com.sparksInTheStep.webBoard.member.presentation;

import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;


@Tag(name="유저 API", description = "유저 마이 페이지 API")
public interface MemberApiSpec {
    @Operation(summary = "유저 닉네임 정보 조회하기", description = "유저의 닉네임을 반환합니다.")
    @ApiResponse(responseCode="200", description = "성공")
    ResponseEntity<?> getNickname(
            @AuthorizedUser MemberInfo.Default memberInfo
    );

    @Operation(summary = "유저 취업 여부 변경", description = "유저의 취업 유무를 변경합니다.")
    @ApiResponse(responseCode="200", description = "성공")
    ResponseEntity<?> updateEmployed(
            @AuthorizedUser MemberInfo.Default memberInfo
    );
}
