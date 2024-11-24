package com.sparksInTheStep.webBoard.auth.presentation.dto;

import com.sparksInTheStep.webBoard.auth.application.dto.MemberInfo;

public record MemberResponse(String nickname) {
    public static MemberResponse from(MemberInfo memberInfo){
        return new MemberResponse(memberInfo.nickname());
    }
}
