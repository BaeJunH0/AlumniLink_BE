package com.sparksInTheStep.webBoard.auth.persistent;

import com.sparksInTheStep.webBoard.auth.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

/*
 * 기본적인 테스트의 흐름
 * Given -> When -> Then
 * 어떤 주어진 상황에서
 * 어떤 동작이 일어났을 때,
 * 다음과 같이 된다 ( Assertion )
 */
@DataJpaTest // Data 사용에 필요한 빈만 사용하는 테스트
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // 각 테스트 실행 후 환경 초기화
public class MemberRepositoryTest {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach // 각 테스트 실행 이전에 설정
    void init(){
        Member member = Member.of("JohnDoe", "password");
        em.persist(member); // EntityManager 가 영속성 관리를 시작
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
}
