package com.sparksInTheStep.webBoard.content.controller;

import com.sparksInTheStep.webBoard.auth.application.dto.UserInfo;
import com.sparksInTheStep.webBoard.content.controller.dto.PostRequest;
import com.sparksInTheStep.webBoard.content.controller.dto.PostResponse;
import com.sparksInTheStep.webBoard.content.domain.Post;
import com.sparksInTheStep.webBoard.content.service.PostService;
import com.sparksInTheStep.webBoard.content.service.dto.PostCommand;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/save")
    public void savePost(@AuthorizedUser UserInfo userinfo, @RequestBody PostRequest request) {
        postService.createPost(userinfo, PostCommand.from(request));
    }
}
