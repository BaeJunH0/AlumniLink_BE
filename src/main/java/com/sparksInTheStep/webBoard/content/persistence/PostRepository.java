package com.sparksInTheStep.webBoard.content.persistence;

import com.sparksInTheStep.webBoard.content.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
