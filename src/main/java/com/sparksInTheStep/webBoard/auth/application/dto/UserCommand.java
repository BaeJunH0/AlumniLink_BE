package com.sparksInTheStep.webBoard.auth.application.dto;

import com.sparksInTheStep.webBoard.auth.domain.User;
import com.sparksInTheStep.webBoard.auth.presentation.dto.UserRequest;

public record UserCommand(String nickname, String password) {
    public static UserCommand from(UserRequest userRequest){
        return new UserCommand(userRequest.nickname(), userRequest.password());
    }
}
