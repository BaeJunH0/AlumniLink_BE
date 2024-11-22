package com.sparksInTheStep.webBoard.auth.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {
    @Test
    @DisplayName("비밀번호 검사 참일 때 테스팅")
    void test1(){
        // given
        Member savedMember = Member.of("JohnDoe", "password");

        // when
        Member checkMember = Member.of("JohnDoe", "password");

        // then
        Assertions.assertThat(savedMember.passCheck(checkMember.getPassword())).isTrue();
    }

    @Test
    @DisplayName("비밀번호 검사 거짓일 때 테스팅")
    void test2(){
        // given
        Member savedMember = Member.of("JohnDoe", "password");

        // when
        Member checkMember = Member.of("JohnDoe", "wrong password");

        // then
        Assertions.assertThat(savedMember.passCheck(checkMember.getPassword())).isFalse();
    }
}
