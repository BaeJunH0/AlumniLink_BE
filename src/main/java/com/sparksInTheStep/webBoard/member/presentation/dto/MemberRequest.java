package com.sparksInTheStep.webBoard.member.presentation.dto;

public record MemberRequest(
        String nickname,
        String password,
        Boolean employed
) {

}
