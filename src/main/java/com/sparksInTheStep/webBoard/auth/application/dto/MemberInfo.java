package com.sparksInTheStep.webBoard.auth.application.dto;

import com.sparksInTheStep.webBoard.auth.domain.Member;

public record MemberInfo(String nickname) {
    public static MemberInfo from(Member member){
        return new MemberInfo(member.getNickname());
    }
}
