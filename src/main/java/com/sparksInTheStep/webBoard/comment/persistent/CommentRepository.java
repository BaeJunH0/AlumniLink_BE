package com.sparksInTheStep.webBoard.comment.persistent;

import com.sparksInTheStep.webBoard.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByPostId(Long postId, Pageable pageable);

    void deleteAllByPostId(Long postId);
}
