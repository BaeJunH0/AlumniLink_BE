package com.sparksInTheStep.webBoard.global.errorHandling.errorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ProjectErrorCode implements ErrorCode{
    NOT_FOUND(HttpStatus.NOT_FOUND, "Pr000", "No such project"),
    NOT_TEAM_LEADER(HttpStatus.FORBIDDEN, "Pr001", "Project can be deleted by only team leader"),
    ALREADY_EXIST_NAME(HttpStatus.FORBIDDEN, "Pr002", "Project's name is already using"),
    ALREADY_JOINED_PROJECT(HttpStatus.FORBIDDEN, "Pr003", "Login user already in this project"),
    OVER_TEAM_SIZE(HttpStatus.FORBIDDEN, "Pr004", "Team is full, check team size"),
    LOGICAL_TEAM_SIZE_ERROR(HttpStatus.FORBIDDEN, "Pr005", "Team member size only can positive integer value");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;

    @Override
    public HttpStatus httpStatus() {
        return this.httpStatus;
    }

    @Override
    public String code() {
        return this.errorCode;
    }

    @Override
    public String message() {
        return this.message;
    }
}
