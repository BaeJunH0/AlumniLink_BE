package com.sparksInTheStep.webBoard.member.presentation.dto;

public record MemberRequest(
        String email,
        String nickname,
        String password,
        Boolean employed,
        String gitLink,
        String resumeLink
) {

}
