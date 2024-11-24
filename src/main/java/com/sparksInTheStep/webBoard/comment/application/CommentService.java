package com.sparksInTheStep.webBoard.comment.application;

import com.sparksInTheStep.webBoard.auth.domain.Member;
import com.sparksInTheStep.webBoard.auth.persistent.MemberRepository;
import com.sparksInTheStep.webBoard.comment.application.dto.CommentCommand;
import com.sparksInTheStep.webBoard.comment.application.dto.CommentInfo;
import com.sparksInTheStep.webBoard.comment.domain.Comment;
import com.sparksInTheStep.webBoard.comment.persistent.CommentRepository;
import com.sparksInTheStep.webBoard.post.persistence.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    public final CommentRepository commentRepository;
    public final PostRepository postRepository;
    public final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<CommentInfo> readCommentsByPostId(Long postId){
        return commentRepository.findAllByPostId(postId).stream()
                .map(CommentInfo::from)
                .toList();
    }

    @Transactional
    public void makeNewComment(CommentCommand commentCommand, String nickname){
        commentRepository.save(Comment.of(
                commentCommand.Body(),
                memberRepository.findByNickname(nickname),
                postRepository.findPostById(commentCommand.postId()).orElseThrow(
                        ()-> new NoSuchFieldError("게시물을 찾을 수 없습니다")
                )
        ));
    }

    @Transactional
    public void deleteComment(String nickname, Long id) throws AuthenticationException {
        Member member = memberRepository.findByNickname(nickname);
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NoSuchFieldError("댓글을 찾을 수 없습니다")
        );

        if(comment.getMember() != member){
            throw new AuthenticationException("자신의 댓글만 지울 수 있습니다");
        }
        commentRepository.deleteById(id);
    }
}
