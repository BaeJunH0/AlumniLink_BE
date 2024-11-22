package com.sparksInTheStep.webBoard.auth.token;

public record Token(String accessToken) {
    public static Token of(String accessToken){
        return new Token(accessToken);
    }
}
