package com.sparksInTheStep.webBoard.member.application.dto;

import com.sparksInTheStep.webBoard.member.presentation.dto.MemberRequest;

public record MemberCommand(String nickname, String password) {
    public static MemberCommand from(MemberRequest memberRequest){
        return new MemberCommand(memberRequest.nickname(), memberRequest.password());
    }
}
