package com.sparksInTheStep.webBoard.comment.application;

import com.sparksInTheStep.webBoard.global.errorHandling.CustomException;
import com.sparksInTheStep.webBoard.global.errorHandling.errorCode.CommentErrorCode;
import com.sparksInTheStep.webBoard.global.errorHandling.errorCode.PostErrorCode;
import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.member.persistent.MemberRepository;
import com.sparksInTheStep.webBoard.comment.application.dto.CommentCommand;
import com.sparksInTheStep.webBoard.comment.application.dto.CommentInfo;
import com.sparksInTheStep.webBoard.comment.domain.Comment;
import com.sparksInTheStep.webBoard.comment.persistent.CommentRepository;
import com.sparksInTheStep.webBoard.post.persistence.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    public final CommentRepository commentRepository;
    public final PostRepository postRepository;
    public final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Page<CommentInfo> readCommentsByPostId(Long postId, Pageable pageable){
        if(isExistId(postId)){
            return commentRepository.findAllByPostId(postId, pageable).map(CommentInfo::from);
        }
        throw CustomException.of(PostErrorCode.NOT_FOUND);
    }

    @Transactional(readOnly = true)
    protected boolean isExistId(Long id){
        return commentRepository.existsById(id);
    }

    @Transactional
    public void makeNewComment(CommentCommand commentCommand, String nickname){
        commentRepository.save(Comment.of(
                commentCommand.Body(),
                memberRepository.findByNickname(nickname),
                postRepository.findPostById(commentCommand.postId()).orElseThrow(
                        () -> CustomException.of(PostErrorCode.NOT_FOUND)
                )
        ));
    }

    @Transactional
    public void deleteComment(String nickname, Long id) {
        Member member = memberRepository.findByNickname(nickname);
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> CustomException.of(CommentErrorCode.NOT_FOUND)
        );

        if(comment.getMember() != member){
            throw CustomException.of(CommentErrorCode.NOT_MY_COMMENT);
        }
        commentRepository.deleteById(id);
    }
}
