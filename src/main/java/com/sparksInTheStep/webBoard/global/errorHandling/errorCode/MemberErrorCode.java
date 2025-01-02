package com.sparksInTheStep.webBoard.global.errorHandling.errorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements ErrorCode{
    NOT_FOUND(HttpStatus.NOT_FOUND, "M000", "member finding fail"),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST, "M001", "already exist nickname"),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "M002", "already exist email"),
    NO_AUTHENTICATION(HttpStatus.FORBIDDEN, "M003", "you're not admin member");

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
