package com.sparksInTheStep.webBoard.content.service;

import com.sparksInTheStep.webBoard.auth.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.auth.domain.Member;
import com.sparksInTheStep.webBoard.auth.persistent.MemberRepository;
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
    private final MemberRepository memberRepository;
    @Transactional
    public void createPost(MemberInfo userInfo, PostCommand postCommand) {
        Member member = memberRepository.findByNickname(userInfo.nickname());
        System.out.println(member.getId()+"dddd");
        member.addPost(new Post(postCommand, member));
    }
}
