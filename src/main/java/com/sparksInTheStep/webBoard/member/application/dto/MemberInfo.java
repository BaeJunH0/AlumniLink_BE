package com.sparksInTheStep.webBoard.member.application.dto;

import com.sparksInTheStep.webBoard.member.domain.Member;

public record MemberInfo() {
    public record Default(String nickname){
        public static MemberInfo.Default from(Member member){
            return new MemberInfo.Default(member.getNickname());
        }
    }

    public record Special(String nickname, Long id, boolean isAdmin){
        public static MemberInfo.Special from(Member member){
            return new MemberInfo.Special(member.getNickname(), member.getId(), member.isAdmin());
        }
    }
}
