package com.sparksInTheStep.webBoard.post.service;

import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.member.persistent.MemberRepository;
import com.sparksInTheStep.webBoard.post.domain.Post;
import com.sparksInTheStep.webBoard.post.persistence.PostRepository;
import com.sparksInTheStep.webBoard.post.service.dto.PostCommand;
import com.sparksInTheStep.webBoard.post.service.dto.PostInfo;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public void createPost(String nickname, PostCommand postCommand) {
        Member member = memberRepository.findByNickname(nickname);

        postRepository.save(new Post(postCommand, member));
    }

    // DB 읽기 동작의 경우에는 readOnly로 할 것
    @Transactional(readOnly = true)
    public List<PostInfo> getPostsByMember(MemberInfo.Default memberInfo) {
        Member member = memberRepository.findByNickname(memberInfo.nickname());

        List<Post> posts = postRepository.findByMember(member);

        return posts.stream()
                .map(PostInfo::from)
                .toList();
    }

    // Post를 전부 가져오기
    @Transactional(readOnly = true)
    public List<PostInfo> getAllPosts(){
        return postRepository.findAll().stream().map(PostInfo::from).toList();
    }

    @Transactional
    public void deletePost(MemberInfo.Default memberInfo, Long postId) throws AuthenticationException {
        Member member = memberRepository.findByNickname(memberInfo.nickname());
        Post post = postRepository.findPostById(postId)
                .orElseThrow(
                        () -> new NoSuchFieldError("해당하는 게시글이 존재하지 않거나 이미 없어진 게시글입니다.")
                );

        // 게시물 작성자 체크
        if(post.getMember() != member){
            throw new AuthenticationException("자신의 게시글만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }
}
