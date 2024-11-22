package com.sparksInTheStep.webBoard.auth.application.dto;

public record MemberInfo(String nickname) {
    public static MemberInfo of(String nickname){
        return new MemberInfo(nickname);
    }
}
