package com.sparksInTheStep.webBoard.auth.presentation;

import com.sparksInTheStep.webBoard.member.presentation.dto.MemberRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "인증 API", description = "로그인 및 회원가입 기능을 담은 API")
public interface AuthApiSpec {
    @Operation(summary = "일반 로그인", description = "아이디와 비밀번호를 사용하여 로그인합니다.")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "401", description = "비밀번호 틀림")
    @ApiResponse(responseCode = "404", description = "존재하지 않는 아이디 사용")
    public ResponseEntity<?> login(
            @Parameter(description = "멤버의 아이디와 비밀번호")
            @RequestBody MemberRequest.Login memberRequest
    );

    @Operation(summary = "회원 가입", description = "아이디와 비밀번호를 사용하여 회원가입합니다.")
    @ApiResponse(responseCode = "201", description = "성공")
    @ApiResponse(responseCode = "400", description = "중복 닉네임 사용")
    public ResponseEntity<?> register(
            @Parameter(description = "멤버의 아이디와 비밀번호")
            @RequestBody MemberRequest.Register memberRequest
    );

    @Operation(summary = "토큰 리프레시", description = "리프레시 토큰을 이용해서 액세스 토큰을 발급합니다")
    @ApiResponse(responseCode = "201", description = "성공")
    public ResponseEntity<?> refresh(
            @RequestParam String refreshToken
    );
}
