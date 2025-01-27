//package com.sparksInTheStep.webBoard.auth.application;
//
//import com.sparksInTheStep.webBoard.member.application.MemberService;
//import com.sparksInTheStep.webBoard.member.application.dto.MemberCommand;
//import com.sparksInTheStep.webBoard.member.domain.Member;
//import com.sparksInTheStep.webBoard.member.persistent.MemberRepository;
//import com.sun.jdi.request.DuplicateRequestException;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.argThat;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.then;
//
//@ExtendWith(MockitoExtension.class)
//public class MemberServiceTest {
//    @Mock
//    private MemberRepository memberRepository;
//    @InjectMocks
//    private MemberService memberService;
//
//    @BeforeEach
//    void init(){
//        // 영속성 관리를 하고 있는 값들을 가정
//        Member member1 = Member.of("JohnDoe", "1234");
//        Member member2 = Member.of("John", "1234");
//        Member member3 = Member.of("Doe", "1234");
//
//        // 이름을 담을 임시 저장소 생성
//        List<String> nameSpace = Arrays.asList("JohnDoe", "John", "Doe");
//
//        // String 클래스 값을 "캡쳐" 하기 위한 객체 생성
//        ArgumentCaptor<String> captor_s = ArgumentCaptor.forClass(String.class);
//
//        // mock 메서드 생성
//        given(memberRepository.existsByNickname(captor_s.capture())).willAnswer(
//                invocation -> {
//                    String value = captor_s.getValue();
//                    if (nameSpace.contains(value)){
//                        return true;
//                    }
//                    return false;
//                }
//        );
//    }
//
//    @Test
//    @DisplayName("isExist 성공 테스트")
//    void test1(){
//        // given
//        /* init() */
//
//        // when
//        boolean isExist = memberService.isExistMember("JohnDoe");
//
//        // then
//        Assertions.assertThat(isExist).isTrue();
//    }
//
//    @Test
//    @DisplayName("isExist 실패 테스트")
//    void test2(){
//        // given
//        /* init() */
//
//        // when
//        boolean isExist = memberService.isExistMember("Nick");
//
//        // then
//        Assertions.assertThat(isExist).isFalse();
//    }
//
//    @Test
//    @DisplayName("makeNewUser 성공 테스트")
//    void test3(){
//        // given
//        /* init() */
//
//        // when
//        MemberCommand memberCommand = new MemberCommand("Nick", "password");
//        memberService.makeNewUser(memberCommand);
//
//        // 비밀번호 검증을 위해 변수 생성
//        UUID password = Member.of("Nick", "password").getPassword();
//
//        // then
//        /*
//         * 2가지를 검증
//         * 1. save() 라는 메서드가 실행되는가,
//         * 2. save() 의 매개 변수의 값이 제대로 들어갔는가
//         */
//        then(memberRepository).should().save(argThat(member ->
//                member.getNickname().equals("Nick") &&
//                        member.getPassword().equals(password)
//                )
//        );
//    }
//
//    @Test
//    @DisplayName("makeNewUser 실패 테스트")
//    void test4(){
//        // given
//        /* init() */
//
//        // when & then
//        MemberCommand memberCommand = new MemberCommand("JohnDoe", "password");
//
//        Assertions.assertThatCode(() -> memberService.makeNewUser(memberCommand))
//                .isInstanceOf(DuplicateRequestException.class);
//    }
//
//    @Test
//    @DisplayName("memberCheck 성공 테스트")
//    void test5(){
//        // given
//        List<String> nameSpace = Arrays.asList("JohnDoe", "John", "Doe");
//
//        ArgumentCaptor<String> captor_s = ArgumentCaptor.forClass(String.class);
//
//        given(memberRepository.findByNickname(captor_s.capture())).willAnswer(
//                invocation -> {
//                    String name = captor_s.getValue();
//                    if(nameSpace.contains(name)){
//                        return Member.of(name, "1111!");
//                    }
//                    return null;
//                }
//        );
//
//        // when
//        MemberCommand memberCommand = new MemberCommand("JohnDoe", "1111!");
//        boolean isMember = memberService.memberCheck(memberCommand);
//
//        // then
//        Assertions.assertThat(isMember).isTrue();
//    }
//
//    @Test
//    @DisplayName("memberCheck 아이디 체크 실패 테스트")
//    void test6(){
//        // given
//        /* init() */
//
//        // when
//        MemberCommand memberCommand = new MemberCommand("Nick", "1111!");
//        boolean isMember = memberService.memberCheck(memberCommand);
//
//        // then
//        Assertions.assertThat(isMember).isFalse();
//    }
//
//    @Test
//    @DisplayName("memberCheck 비밀번호 체크 실패 테스트")
//    void test7(){
//        // given
//        List<String> nameSpace = Arrays.asList("JohnDoe", "John", "Doe");
//
//        ArgumentCaptor<String> captor_s = ArgumentCaptor.forClass(String.class);
//
//        given(memberRepository.findByNickname(captor_s.capture())).willAnswer(
//                invocation -> {
//                    String name = captor_s.getValue();
//                    if(nameSpace.contains(name)){
//                        return Member.of(name, "1111!");
//                    }
//                    return null;
//                }
//        );
//
//        // when
//        MemberCommand memberCommand = new MemberCommand("JohnDoe", "1111@");
//        boolean isMember = memberService.memberCheck(memberCommand);
//
//        // then
//        Assertions.assertThat(isMember).isFalse();
//    }
//}
