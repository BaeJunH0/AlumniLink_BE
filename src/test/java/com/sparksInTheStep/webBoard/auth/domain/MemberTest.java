package com.sparksInTheStep.webBoard.auth.domain;

import com.sparksInTheStep.webBoard.member.application.dto.MemberCommand;
import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.member.presentation.dto.MemberRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {
    @Test
    @DisplayName("비밀번호 검사 참일 때 테스팅")
    void test1(){
        // given
        MemberRequest memberRequest = new MemberRequest(
                "email",
                "JohnDoe",
                "1234",
                true,
                "git",
                "resume"
        );
        MemberCommand memberCommand = MemberCommand.from(memberRequest);
        Member savedMember = Member.of(memberCommand);

        // when
        MemberRequest checkMemberRequest = new MemberRequest(
                "email",
                null,
                "1234",
                null,
                null,
                null
        );
        MemberCommand checkMemberCommand = MemberCommand.from(checkMemberRequest);
        Member checkMember = Member.of(checkMemberCommand);

        // then
        Assertions.assertThat(savedMember.passCheck(checkMember.getPassword())).isTrue();
    }

    @Test
    @DisplayName("비밀번호 검사 거짓일 때 테스팅")
    void test2(){
        // given
        MemberRequest memberRequest = new MemberRequest(
                "email",
                "JohnDoe",
                "1234",
                true,
                "git",
                "resume"
        );
        MemberCommand memberCommand = MemberCommand.from(memberRequest);
        Member savedMember = Member.of(memberCommand);

        // when
        MemberRequest checkMemberRequest = new MemberRequest(
                "email",
                null,
                "1234!",
                null,
                null,
                null
        );
        MemberCommand checkMemberCommand = MemberCommand.from(checkMemberRequest);
        Member checkMember = Member.of(checkMemberCommand);

        // then
        Assertions.assertThat(savedMember.passCheck(checkMember.getPassword())).isFalse();
    }
}
