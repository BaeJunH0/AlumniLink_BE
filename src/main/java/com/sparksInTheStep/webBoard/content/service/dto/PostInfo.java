package com.sparksInTheStep.webBoard.content.service.dto;

public record PostInfo(String title){
    public static PostInfo of(String title){
        return new PostInfo(title);
    }
}
