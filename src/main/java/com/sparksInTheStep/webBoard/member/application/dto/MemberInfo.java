package com.sparksInTheStep.webBoard.member.application.dto;

import com.sparksInTheStep.webBoard.member.domain.Member;

public record MemberInfo() {
    public record Default(String email, String nickname, Boolean employed){
        public static MemberInfo.Default from(Member member){
            return new MemberInfo.Default(member.getEmail(),member.getNickname(), member.isEmployed());
        }
    }

    public record Special(String email, String nickname, Long id, boolean isAdmin){
        public static MemberInfo.Special from(Member member){
            return new MemberInfo.Special(
                    member.getEmail(), member.getNickname(), member.getId(), member.isAdmin());
        }
    }
}
