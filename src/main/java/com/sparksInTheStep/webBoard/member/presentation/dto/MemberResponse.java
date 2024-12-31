package com.sparksInTheStep.webBoard.member.presentation.dto;

import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;

public record MemberResponse() {
    public record Default(String nickname, Boolean employed){
        public static MemberResponse.Default from(MemberInfo.Default memberInfo){
            return new MemberResponse.Default(memberInfo.nickname(), memberInfo.employed());
        }
    }

    public record Special(String nickname, Long id, boolean isAdmin){
        public static MemberResponse.Special from(MemberInfo.Special memberInfo){
            return new MemberResponse.Special(memberInfo.nickname(), memberInfo.id(), memberInfo.isAdmin());
        }
    }
}
