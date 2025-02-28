package com.sparksInTheStep.webBoard.post.application;

import com.sparksInTheStep.webBoard.global.errorHandling.CustomException;
import com.sparksInTheStep.webBoard.global.errorHandling.errorCode.PostErrorCode;
import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.member.persistent.MemberRepository;
import com.sparksInTheStep.webBoard.post.domain.Post;
import com.sparksInTheStep.webBoard.post.persistence.PostRepository;
import com.sparksInTheStep.webBoard.post.application.dto.PostCommand;
import com.sparksInTheStep.webBoard.post.application.dto.PostInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createPost(String nickname, PostCommand postCommand) {
        Member member = memberRepository.findByNickname(nickname);

        postRepository.save(Post.from(postCommand, member));
    }

    @Transactional(readOnly = true)
    public Page<PostInfo> getPostsByMember(String nickname, Pageable pageable) {
        Member member = memberRepository.findByNickname(nickname);
        Page<Post> posts = postRepository.findByMember(member, pageable);

        return posts.map(PostInfo::from);
    }

    @Transactional(readOnly = true)
    public Page<PostInfo> getAllPosts(Pageable pageable){
        return postRepository.findAll(pageable).map(PostInfo::from);
    }

    @Transactional(readOnly = true)
    public PostInfo getOnePost(Long id){
        return PostInfo.from(postRepository.findById(id).orElseThrow(
                ()-> CustomException.of(PostErrorCode.NOT_FOUND)
        ));
    }

    @Transactional
    public void updatePost(String nickname, Long postId, PostCommand postCommand){
        Post post = postRepository.findPostById(postId).orElseThrow(
                () -> CustomException.of(PostErrorCode.NOT_FOUND)
        );

        isOwner(nickname, postId);
        post.update(
                postCommand.title(), postCommand.tag(), postCommand.body(), postCommand.description()
        );
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findPostById(postId).orElseThrow(
                () -> CustomException.of(PostErrorCode.NOT_FOUND)
        );
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public void isOwner(String nickname, Long postId) {
        Post post = postRepository.findPostById(postId).orElseThrow(
                () -> CustomException.of(PostErrorCode.NOT_FOUND)
        );

        if(!post.getMember().getNickname().equals(nickname)){
            throw CustomException.of(PostErrorCode.NOT_MY_COMMENT);
        }
    }
}
