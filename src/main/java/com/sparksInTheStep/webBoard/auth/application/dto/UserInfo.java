package com.sparksInTheStep.webBoard.auth.application.dto;

public record UserInfo(String nickname) {
    public static UserInfo of(String nickname){
        return new UserInfo(nickname);
    }
}
