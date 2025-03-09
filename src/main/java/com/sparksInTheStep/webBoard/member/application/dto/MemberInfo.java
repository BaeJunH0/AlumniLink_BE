package com.sparksInTheStep.webBoard.member.application.dto;

import com.sparksInTheStep.webBoard.member.domain.Member;

public record MemberInfo(
        Long id,
        String nickname,
        String email,
        boolean admin,
        boolean employed,
        String gitLink,
        String resumeLink,
        boolean isAdmin
) {
    public static MemberInfo of(Member member) {
        return new MemberInfo(
                member.getId(),
                member.getNickname(),
                member.getEmail(),
                member.isAdmin(),
                member.isEmployed(),
                member.getGitLink(),
                member.getResumeLink(),
                member.isAdmin()
        );
    }
}
