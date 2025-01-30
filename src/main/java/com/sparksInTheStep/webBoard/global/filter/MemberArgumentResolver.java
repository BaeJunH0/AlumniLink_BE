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
        // 헤더가 Authorization 필드를 가지는지 확인
        String token = webRequest.getHeader("Authorization");
        if (token == null) {
            throw CustomException.of(AuthErrorCode.TOKEN_IS_EMPTY);
        }
        // Bearer 로 시작하는 유효한 토큰인지 확인
        if (!token.startsWith("Bearer ")){
            throw CustomException.of(AuthErrorCode.TOKEN_IS_INVALID);
        }

        // 실제 토큰 내용 추출
        token = token.substring(7); // "Bearer " 부분을 제거
        String email = jwtTokenProvider.getEmailFromToken(token);
        String nickname = jwtTokenProvider.getNicknameFromToken(token);

        // 존재하는 email 인지 검사
        if(!memberRepository.existsByEmail(email)) {
            throw CustomException.of(MemberErrorCode.NOT_FOUND);
        }

        // 존재하는 nickname 인지 검사
        if(!memberRepository.existsByNickname(nickname)) {
            throw CustomException.of(MemberErrorCode.NOT_FOUND);
        }

        // 토큰 타입에 따른 동작
        String type = jwtTokenProvider.getTypeFromToken(token);
        // 리프레시 동작
        if(type.equals("refresh")) {
            HttpServletResponse response = (HttpServletResponse) webRequest.getNativeResponse();

            String newAccessToken = jwtTokenProvider.makeAccessToken(email, nickname);
            Objects.requireNonNull(response).setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"accessToken\": \"" + newAccessToken + "\"}");
            response.getWriter().flush();

            mavContainer.setRequestHandled(true);
        }
        // 액세스 동작
        return MemberInfo.Default.from(memberRepository.findByEmail(email));
    }
}