package com.sparksInTheStep.webBoard.global.errorHandling;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus httpStatus();

    String code();

    String message();
}
