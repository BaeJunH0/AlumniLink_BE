package com.sparksInTheStep.webBoard.auth.util;

public record Token(String accessToken) {
    public static Token of(String accessToken){
        return new Token(accessToken);
    }
}
