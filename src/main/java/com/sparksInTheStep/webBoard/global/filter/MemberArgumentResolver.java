package com.sparksInTheStep.webBoard.global.filter;

import com.sparksInTheStep.webBoard.auth.token.JwtTokenProvider;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import com.sparksInTheStep.webBoard.global.errorHandling.CustomException;
import com.sparksInTheStep.webBoard.global.errorHandling.errorCode.AuthErrorCode;
import com.sparksInTheStep.webBoard.global.errorHandling.errorCode.MemberErrorCode;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.member.persistent.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 검증 대상 어노테이션 등록
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthorizedUser.class);
    }

    // 동작 정의
    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws IOException {
        // 원활한 테스트를 위한 인증 기능 비활성화
        return MemberInfo.Default.from(memberRepository.findById(1L).orElseThrow());
//        // 헤더가 Authorization 필드를 가지는지 확인
//        String token = webRequest.getHeader("Authorization");
//        if (token == null) {
//            throw CustomException.of(AuthErrorCode.TOKEN_IS_EMPTY);
//        }
//        // Bearer 로 시작하는 유효한 토큰인지 확인
//        if (!token.startsWith("Bearer ")){
//            throw CustomException.of(AuthErrorCode.TOKEN_IS_INVALID);
//        }
//
//        // 실제 토큰 내용 추출
//        token = token.substring(7); // "Bearer " 부분을 제거
//        String email = jwtTokenProvider.getEmailFromToken(token);
//        String nickname = jwtTokenProvider.getNicknameFromToken(token);
//
//        // 유효 시간을 지났는지 검사
//        if(jwtTokenProvider.isTokenExpired(token)) {
//            throw CustomException.of(AuthErrorCode.TOKEN_TIMEOUT);
//        }
//
//        // 존재하는 email 인지 검사
//        if(!memberRepository.existsByEmail(email)) {
//            throw CustomException.of(MemberErrorCode.NOT_FOUND);
//        }
//
//        // 존재하는 nickname 인지 검사
//        if(!memberRepository.existsByNickname(nickname)) {
//            throw CustomException.of(MemberErrorCode.NOT_FOUND);
//        }
//
//        // 동작
//        return MemberInfo.Default.from(memberRepository.findByEmail(email));
    }
}