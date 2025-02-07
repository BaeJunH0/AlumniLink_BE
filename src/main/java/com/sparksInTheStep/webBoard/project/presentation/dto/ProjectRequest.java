package com.sparksInTheStep.webBoard.project.presentation.dto;

import java.util.Date;

public record ProjectRequest(
        String name,
        String info,
        String link,
        int maxCount,
        Date deadline
) {
}
