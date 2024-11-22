package com.sparksInTheStep.webBoard.auth.application.dto;

import com.sparksInTheStep.webBoard.auth.presentation.dto.MemberRequest;

public record MemberCommand(String nickname, String password) {
    public static MemberCommand from(MemberRequest memberRequest){
        return new MemberCommand(memberRequest.nickname(), memberRequest.password());
    }
}
