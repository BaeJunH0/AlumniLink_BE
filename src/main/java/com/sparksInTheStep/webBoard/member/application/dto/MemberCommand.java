package com.sparksInTheStep.webBoard.member.application.dto;

import com.sparksInTheStep.webBoard.member.presentation.dto.MemberRequest;

public record MemberCommand(
        String email,
        String nickname,
        String password,
        Boolean employed,
        String gitLink,
        String resumeLink) {
    public static MemberCommand from(MemberRequest.Register memberRequest){
        return new MemberCommand(
                memberRequest.email(),
                memberRequest.nickname(),
                memberRequest.password(),
                memberRequest.employed(),
                memberRequest.gitLink(),
                memberRequest.resumeLink()
        );
    }

    public static MemberCommand from(MemberRequest.Login memberRequest){
        return new MemberCommand(
                memberRequest.email(),
                null,
                memberRequest.password(),
                null,
                null,
                null
        );
    }
}
