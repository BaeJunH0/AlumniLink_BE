package com.sparksInTheStep.webBoard.auth.persistent;

import com.sparksInTheStep.webBoard.member.application.dto.MemberCommand;
import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.member.persistent.MemberRepository;
import com.sparksInTheStep.webBoard.member.presentation.dto.MemberRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MemberRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void init(){
        MemberRequest memberRequest = new MemberRequest(
                "email",
                "JohnDoe",
                "1234",
                true,
                "git",
                "resume"
        );
        MemberCommand memberCommand = MemberCommand.from(memberRequest);
        Member member = Member.of(memberCommand);
        em.persist(member);
    }

    @Test
    @DisplayName("existsByNickname = true 테스트")
    void test1() {
        // given
        /* init() */

        // when
        boolean isExist = memberRepository.existsByNickname("JohnDoe");

        // then
        Assertions.assertThat(isExist).isTrue();
    }

    @Test
    @DisplayName("existsByNickname = false 테스트")
    void test2() {
        // given
        /* init() */

        // when
        boolean isExist = memberRepository.existsByNickname("Nick");

        // then
        Assertions.assertThat(isExist).isFalse();
    }

    @Test
    @DisplayName("findByNickname 성공 테스트")
    void test3() {
        // given
        /* init() */

        // when
        Member member = memberRepository.findByNickname("JohnDoe");

        // then
        Assertions.assertThat(member.getId()).isEqualTo(1L);
        Assertions.assertThat(member.getNickname()).isEqualTo("JohnDoe");
    }

    @Test
    @DisplayName("findByNickname 실패 테스트")
    void test4(){
        // given
        /* init() */

        // when
        Member member = memberRepository.findByNickname("Nick");

        // then
        Assertions.assertThat(member).isNull();
    }

    @Test
    @DisplayName("existsByEmail = true 테스트")
    void test5() {
        // given
        /* init() */

        // when
        boolean isExist = memberRepository.existsByEmail("email");

        // then
        Assertions.assertThat(isExist).isTrue();
    }

    @Test
    @DisplayName("existsByEmail = false 테스트")
    void test6() {
        // given
        /* init() */

        // when
        boolean isExist = memberRepository.existsByNickname("mail");

        // then
        Assertions.assertThat(isExist).isFalse();
    }

    @Test
    @DisplayName("findByEmail 성공 테스트")
    void test7() {
        // given
        /* init() */

        // when
        Member member = memberRepository.findByEmail("email");

        // then
        Assertions.assertThat(member.getId()).isEqualTo(1L);
        Assertions.assertThat(member.getNickname()).isEqualTo("JohnDoe");
    }

    @Test
    @DisplayName("findByEmail 실패 테스트")
    void test8(){
        // given
        /* init() */

        // when
        Member member = memberRepository.findByNickname("mail");

        // then
        Assertions.assertThat(member).isNull();
    }
}
