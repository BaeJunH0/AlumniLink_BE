package com.sparksInTheStep.webBoard.post.persistence;

import com.sparksInTheStep.webBoard.auth.domain.Member;
import com.sparksInTheStep.webBoard.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByMember(Member member);

    Optional<Post> findPostById(Long id);
}
