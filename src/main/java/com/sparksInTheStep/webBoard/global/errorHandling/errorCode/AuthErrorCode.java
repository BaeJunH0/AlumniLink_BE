package com.sparksInTheStep.webBoard.global.errorHandling.errorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AuthErrorCode implements ErrorCode{
    NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED, "A000", "Authorization Fail"),
    TOKEN_IS_EMPTY(HttpStatus.BAD_REQUEST, "A001", "not include token"),
    TOKEN_IS_INVALID(HttpStatus.BAD_REQUEST, "A002", "is not valid auth method (maybe not bearer)");

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
