package com.sparksInTheStep.webBoard.global.filter;

import com.sparksInTheStep.webBoard.auth.application.MemberService;
import com.sparksInTheStep.webBoard.auth.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.auth.util.JwtUtil;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthorizedUser.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        String token = webRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("인증 방식이 잘못되었거나, 토큰이 없습니다!");
        }

        token = token.substring(7); // "Bearer " 부분을 제거

        String nickname = jwtTokenProvider.getNicknameFromToken(token);
        if(!memberService.isExistMember(nickname)) {
            throw new IllegalArgumentException("유효하지 않은 로그인 정보입니다!");
        }

        return MemberInfo.of(nickname);
    }
}
