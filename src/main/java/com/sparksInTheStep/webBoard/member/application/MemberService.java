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

    @Transactional
    public void makeNewUser(MemberCommand memberCommand){
        if(isExistMember(memberCommand.nickname())){
            throw CustomException.of(MemberErrorCode.DUPLICATED_NICKNAME);
        }

        memberRepository.save(Member.of(memberCommand.nickname(), memberCommand.password()));
    }

    @Transactional(readOnly = true)
    public boolean memberCheck(MemberCommand memberCommand){
        if(isExistMember(memberCommand.nickname())){
            Member savedMember = memberRepository.findByNickname(memberCommand.nickname());
            Member checkMember = Member.of(memberCommand.nickname(), memberCommand.password());

            return savedMember.passCheck(checkMember.getPassword());
        }
        return false;
    }

    @Transactional(readOnly = true)
    public boolean adminCheck(MemberCommand memberCommand){
        if(!isNotAdminMember(memberCommand.nickname())){
            Member savedMember = memberRepository.findByNickname(memberCommand.nickname());
            Member checkMember = Member.of(memberCommand.nickname(), memberCommand.password());

            return savedMember.passCheck(checkMember.getPassword());
        }
        return false;
    }

    // 관리자용
    @Transactional(readOnly = true)
    public Page<MemberInfo.Special> readAllMembers(String nickname, Pageable pageable){
        if(isNotAdminMember(nickname)){
            throw CustomException.of(MemberErrorCode.NO_AUTHENTICATION);
        }

        return memberRepository.findAll(pageable).map(MemberInfo.Special::from);
    }

    // 관리자용
    @Transactional
    public void grantingMember(String adminNickname, Long memberId) {
        if(isNotAdminMember(adminNickname)){
            throw CustomException.of(MemberErrorCode.NO_AUTHENTICATION);
        }

        Member savedMember = memberRepository.findById(memberId).orElseThrow(
                () -> CustomException.of(MemberErrorCode.NOT_FOUND)
        );
        savedMember.granting();
    }

    // 관리자용
    @Transactional
    public void deleteMember(String adminNickname, Long memberId) {
        if(isNotAdminMember(adminNickname)){
            throw CustomException.of(MemberErrorCode.NO_AUTHENTICATION);
        }

        memberRepository.deleteById(memberId);
    }

    @Transactional(readOnly = true)
    public MemberInfo.Default loginMember(String nickname){
        return MemberInfo.Default.from(memberRepository.findByNickname(nickname));
    }

    @Transactional(readOnly = true)
    public boolean isExistMember(String nickname){
        return memberRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public boolean isNotAdminMember(String nickname){
        if(!isExistMember(nickname)){
            throw CustomException.of(MemberErrorCode.NOT_FOUND);
        }

        return !memberRepository.findByNickname(nickname).adminCheck();
    }
}
