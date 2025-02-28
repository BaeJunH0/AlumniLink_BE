package com.sparksInTheStep.webBoard.member.persistent;

import com.sparksInTheStep.webBoard.member.domain.Member;
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

@DataJpaTest
@Sql("classpath:sql/member-test.sql")
@ActiveProfiles("test")
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("findAll test")
    void test1() {
        // given
        Pageable pageable = PageRequest.of(0, 3);
        final int allContentSize = 5;
        final int pageObjectSize = 3;

        // when
        Page<Member> members = memberRepository.findAll(pageable);

        // then
        Assertions.assertThat(members.getContent().size()).isEqualTo(pageObjectSize);
        Assertions.assertThat(members.getTotalElements()).isEqualTo(allContentSize);
    }

    @Test
    @DisplayName("existsByNickname success test")
    void test2() {
        // given

        // when
        boolean tf = memberRepository.existsByNickname("user_one");

        // then
        Assertions.assertThat(tf).isTrue();
    }

    @Test
    @DisplayName("existsByNickname fail test")
    void test3() {
        // given

        // when
        boolean tf = memberRepository.existsByNickname("john_doe");

        // then
        Assertions.assertThat(tf).isFalse();
    }

    @Test
    @DisplayName("existsByEmail success test")
    void test4() {
        // given

        // when
        boolean tf = memberRepository.existsByEmail("user1@example.com");

        // then
        Assertions.assertThat(tf).isTrue();
    }

    @Test
    @DisplayName("existsByEmail fail test")
    void test5() {
        // given

        // when
        boolean tf = memberRepository.existsByEmail("john_doe@email.com");

        // then
        Assertions.assertThat(tf).isFalse();
    }

    @Test
    @DisplayName("findByNickname success test")
    void test6() {
        // given
        final String email = "user1@example.com";
        final String gitLink = "https://github.com/user1";
        final String resumeLink = "https://resume.com/user1";

        // when
        Member member = memberRepository.findByNickname("user_one");

        // then
        Assertions.assertThat(member.getEmail()).isEqualTo(email);
        Assertions.assertThat(member.getGitLink()).isEqualTo(gitLink);
        Assertions.assertThat(member.getResumeLink()).isEqualTo(resumeLink);
    }

    @Test
    @DisplayName("findByNickname fail test")
    void test7() {
        // given

        // when
        Member member = memberRepository.findByNickname(" ");

        // then
        Assertions.assertThat(member).isNull();
    }

    @Test
    @DisplayName("findByEmail success test")
    void test8() {
        // given
        final String nickname = "user_one";
        final String gitLink = "https://github.com/user1";
        final String resumeLink = "https://resume.com/user1";

        // when
        Member member = memberRepository.findByEmail("user1@example.com");

        // then
        Assertions.assertThat(member.getNickname()).isEqualTo(nickname);
        Assertions.assertThat(member.getGitLink()).isEqualTo(gitLink);
        Assertions.assertThat(member.getResumeLink()).isEqualTo(resumeLink);
    }

    @Test
    @DisplayName("findByEmail fail test")
    void test9() {
        // given

        // when
        Member member = memberRepository.findByEmail(" ");

        // then
        Assertions.assertThat(member).isNull();
    }
}
