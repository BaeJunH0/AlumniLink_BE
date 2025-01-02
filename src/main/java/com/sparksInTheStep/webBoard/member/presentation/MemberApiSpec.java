package com.sparksInTheStep.webBoard.member.presentation;

import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.member.presentation.dto.MemberRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name="유저 API", description = "유저 마이 페이지 API")
public interface MemberApiSpec {
    @Operation(summary = "유저 정보 변경", description = "유저의 정보를 변경합니다.")
    @ApiResponse(responseCode="200", description = "성공")
    ResponseEntity<?> updateMember(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @RequestBody MemberRequest memberRequest
    );
}
