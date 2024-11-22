package com.sparksInTheStep.webBoard.content.controller.dto;

import java.util.Date;

public record PostResponse(
        String nickname,
        String title,
        String body,
        String tag,
        Date startTime,
        Date modifiedTime
) {

}
