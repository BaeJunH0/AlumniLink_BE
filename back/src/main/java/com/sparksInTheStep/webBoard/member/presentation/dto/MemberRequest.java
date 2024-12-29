package com.sparksInTheStep.webBoard.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberRequest(
        @NotBlank
        String nickname,
        @NotBlank
        String password
) {

}
