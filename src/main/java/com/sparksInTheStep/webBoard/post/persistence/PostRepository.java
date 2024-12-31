package com.sparksInTheStep.webBoard.post.persistence;

import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    Page<Post> findByMember(Member member, Pageable pageable);

    Optional<Post> findPostById(Long id);
}
