package com.sparksInTheStep.webBoard.member.presentation.dto;

public class MemberRequest {
    public record Login(
            String email,
            String password
    ) {}

    public record Register(
            String email,
            String nickname,
            String password,
            Boolean employed,
            String gitLink,
            String resumeLink
    ) {}
}
