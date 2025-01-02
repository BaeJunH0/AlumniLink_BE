package com.sparksInTheStep.webBoard.member.application.dto;

import com.sparksInTheStep.webBoard.member.presentation.dto.MemberRequest;

public record MemberCommand(String email, String nickname, String password, Boolean employed) {
    public static MemberCommand from(MemberRequest memberRequest){
        return new MemberCommand(
                memberRequest.email(),
                memberRequest.nickname(),
                memberRequest.password(),
                memberRequest.employed()
        );
    }
}
