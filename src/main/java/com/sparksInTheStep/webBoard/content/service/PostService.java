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
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public void createPost(MemberInfo memberInfo, PostCommand postCommand) {
        Member member = memberRepository.findByNickname(memberInfo.nickname());

        postRepository.save(new Post(postCommand, member));
    }

    @Transactional
    public List<PostResponse> getPostsByMember(MemberInfo memberInfo) {
        Member member = memberRepository.findByNickname(memberInfo.nickname());

        List<Post> posts = postRepository.findByMember(member);

        return posts.stream()
                .map(PostResponse::from)
                .toList();
    }

    @Transactional
    public void deletePost(MemberInfo memberInfo, Long postId) {
        Member member = memberRepository.findByNickname(memberInfo.nickname());
        Post post = postRepository.findPostById(postId)
                .orElseThrow(() -> new NullPointerException("해당하는 게시글이 존재하지 않거나 이미 없어진 게시글입니다."));
        postRepository.delete(post);
    }
}
