package com.sparksInTheStep.webBoard.member.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberRequest(
        String nickname,

        String password
) {

}
