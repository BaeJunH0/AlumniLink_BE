package com.sparksInTheStep.webBoard.post.persistent;

import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.member.persistent.MemberRepository;
import com.sparksInTheStep.webBoard.post.domain.Post;
import com.sparksInTheStep.webBoard.post.persistence.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

@DataJpaTest
@Sql("classpath:sql/post-test.sql")
@ActiveProfiles("test")
public class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("findAll test")
    void test1() {
        // given
        Pageable pageable = PageRequest.of(0, 5);
        final int allContentSize = 10;
        final int pageObjectSize = 5;

        // when
        Page<Post> posts = postRepository.findAll(pageable);

        // then
        Assertions.assertThat(posts.getContent().size()).isEqualTo(pageObjectSize);
        Assertions.assertThat(posts.getTotalElements()).isEqualTo(allContentSize);
    }

    @Test
    @DisplayName("findByMember test")
    void test2() {
        // given
        Pageable pageable = PageRequest.of(0, 5);
        final int allContentSize = 2;
        final int pageObjectSize = 2;

        // when
        Member member = memberRepository.findByNickname("user_one");
        Page<Post> posts = postRepository.findByMember(member, pageable);

        // then
        Assertions.assertThat(posts.getContent().size()).isEqualTo(pageObjectSize);
        Assertions.assertThat(posts.getTotalElements()).isEqualTo(allContentSize);
        Assertions.assertThat(posts.getContent().get(0).getId()).isEqualTo(2L);
        Assertions.assertThat(posts.getContent().get(1).getId()).isEqualTo(7L);
    }

    @Test
    @DisplayName("findPostById success test")
    void test3() {
        // given
        LocalDateTime expectedDateTime = LocalDateTime.of(2024, 2, 17, 10, 30, 0, 0);
        // when
        Post post = postRepository.findPostById(9L).orElseThrow();

        // then
        Assertions.assertThat(post.getAtCreated()).isEqualTo(expectedDateTime);
    }

    @Test
    @DisplayName("findPostById fail test")
    void test4() {
        // given

        // when

        // then
        Assertions.assertThatThrownBy(()-> {
                    Post post = postRepository.findPostById(11L).orElseThrow(NullPointerException::new);
        })
        .isInstanceOf(NullPointerException.class);
    }
}
