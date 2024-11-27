package com.sparksInTheStep.webBoard.global.errorHandling.errorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CommentErrorCode implements ErrorCode{
    NOT_FOUND(HttpStatus.NOT_FOUND, "C000", "No such comment"),
    NOT_MY_COMMENT(HttpStatus.FORBIDDEN, "C001", "You can delete only your comment");

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
