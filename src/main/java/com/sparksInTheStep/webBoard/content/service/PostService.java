package com.sparksInTheStep.webBoard.content.service;

import com.sparksInTheStep.webBoard.auth.application.dto.UserInfo;
import com.sparksInTheStep.webBoard.auth.domain.User;
import com.sparksInTheStep.webBoard.auth.persistent.UserRepository;
import com.sparksInTheStep.webBoard.content.controller.dto.PostRequest;
import com.sparksInTheStep.webBoard.content.controller.dto.PostResponse;
import com.sparksInTheStep.webBoard.content.domain.Post;
import com.sparksInTheStep.webBoard.content.persistence.PostRepository;
import com.sparksInTheStep.webBoard.content.service.dto.PostCommand;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createPost(UserInfo userInfo, PostCommand postCommand) {
        User user = userRepository.findByNickname(userInfo.nickname());
        user.addPost(new Post(postCommand));
    }
}
