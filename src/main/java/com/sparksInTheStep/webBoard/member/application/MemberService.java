package com.sparksInTheStep.webBoard.member.application;

import com.sparksInTheStep.webBoard.member.application.dto.MemberCommand;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.member.domain.Member;
import com.sparksInTheStep.webBoard.member.persistent.MemberRepository;
import com.sparksInTheStep.webBoard.global.errorHandling.CustomException;
import com.sparksInTheStep.webBoard.global.errorHandling.errorCode.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 로그인
    @Transactional(readOnly = true)
    public boolean memberCheck(MemberCommand memberCommand){
        // 아이디 ( 이메일 ) 검증
        if(memberRepository.existsByEmail(memberCommand.email())){
            // 비밀번호 검증
            Member savedMember = memberRepository.findByEmail(memberCommand.email());
            Member checkMember = Member.of(memberCommand);
            return savedMember.passCheck(checkMember.getPassword());
        }
        return false;
    }

    // 회원가입
    @Transactional
    public void makeNewUser(MemberCommand memberCommand){
        // 이메일 중복 검사
        if(memberRepository.existsByEmail(memberCommand.email())) {
            throw CustomException.of(MemberErrorCode.DUPLICATED_EMAIL);
        }
        // 닉네임 중복 검사
        if(memberRepository.existsByNickname(memberCommand.nickname())) {
            throw CustomException.of(MemberErrorCode.DUPLICATED_NICKNAME);
        }

        memberRepository.save(Member.of(memberCommand));
    }

    // 회원 정보 수정
    @Transactional
    public void updateMember(String email, MemberCommand memberCommand) {
        Member member = memberRepository.findByEmail(email);

        member.update(
                memberCommand.nickname(),
                memberCommand.password(),
                memberCommand.employed(),
                memberCommand.gitLink(),
                memberCommand.resumeLink()
        );
    }

    // 관리자 로그인
    @Transactional(readOnly = true)
    public boolean adminCheck(MemberCommand memberCommand){
        return memberCheck(memberCommand) && isAdminMember(memberCommand.email());
    }

    @Transactional(readOnly = true)
    public Page<MemberInfo.Special> readAllMembers(String email, Pageable pageable){
        if(isAdminMember(email)){
            return memberRepository.findAll(pageable).map(MemberInfo.Special::from);
        }
        throw CustomException.of(MemberErrorCode.NO_AUTHENTICATION);
    }

    @Transactional
    public void grantingMember(String email, Long memberId) {
        if(isAdminMember(email)){
            Member savedMember = memberRepository.findById(memberId).orElseThrow(
                    () -> CustomException.of(MemberErrorCode.NOT_FOUND)
            );
            savedMember.granting();
        }

        throw CustomException.of(MemberErrorCode.NO_AUTHENTICATION);
    }

    @Transactional
    public void deleteMember(String email, Long memberId) {
        if(isAdminMember(email)){
            memberRepository.deleteById(memberId);
        }

        throw CustomException.of(MemberErrorCode.NO_AUTHENTICATION);
    }

    @Transactional(readOnly = true)
    public String getMemberName(String email) {
        Member member = memberRepository.findByEmail(email);

        return member.getNickname();
    }

    @Transactional(readOnly = true)
    public boolean isAdminMember(String email){
        return memberRepository.findByEmail(email).adminCheck();
    }
}
