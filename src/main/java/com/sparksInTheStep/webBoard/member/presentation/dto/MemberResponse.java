package com.sparksInTheStep.webBoard.member.presentation.dto;

import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;

public record MemberResponse() {
    public record Default(String email, String nickname, Boolean employed){
        public static MemberResponse.Default from(MemberInfo.Default memberInfo){
            return new MemberResponse.Default(
                    memberInfo.email(), memberInfo.nickname(), memberInfo.employed()
            );
        }
    }

    public record Special(String email, String nickname, Long id, boolean isAdmin){
        public static MemberResponse.Special from(MemberInfo.Special memberInfo){
            return new MemberResponse.Special(
                    memberInfo.email(), memberInfo.nickname(), memberInfo.id(), memberInfo.isAdmin()
            );
        }
    }
}
