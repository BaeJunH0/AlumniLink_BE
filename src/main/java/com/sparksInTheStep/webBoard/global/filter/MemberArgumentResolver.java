package com.sparksInTheStep.webBoard.global.filter;

import com.sparksInTheStep.webBoard.member.application.MemberService;
import com.sparksInTheStep.webBoard.auth.token.JwtTokenProvider;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import com.sparksInTheStep.webBoard.global.errorHandling.CustomException;
import com.sparksInTheStep.webBoard.global.errorHandling.errorCode.AuthErrorCode;
import com.sparksInTheStep.webBoard.global.errorHandling.errorCode.MemberErrorCode;
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
        if (token == null) {
            throw CustomException.of(AuthErrorCode.TOKEN_IS_EMPTY);
        }
        if (!token.startsWith("Bearer ")){
            throw CustomException.of(AuthErrorCode.TOKEN_IS_INVALID);
        }

        token = token.substring(7); // "Bearer " 부분을 제거
        String nickname = jwtTokenProvider.getNicknameFromToken(token);
        if(!memberService.isExistMember(nickname)) {
            throw CustomException.of(MemberErrorCode.NOT_FOUND);
        }

        return memberService.loginMember(nickname);
    }
}