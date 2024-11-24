package com.sparksInTheStep.webBoard.auth.application;

import com.sparksInTheStep.webBoard.auth.application.dto.MemberCommand;
import com.sparksInTheStep.webBoard.auth.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.auth.domain.Member;
import com.sparksInTheStep.webBoard.auth.persistent.MemberRepository;
import com.sparksInTheStep.webBoard.global.errorHandling.CustomException;
import com.sparksInTheStep.webBoard.global.errorHandling.errorCode.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    // 관리자용
    @Transactional(readOnly = true)
    public List<MemberInfo> readAllMembers(String nickname){
        if(!isAdminMember(nickname)){
            throw CustomException.of(MemberErrorCode.NO_AUTHENTICATION);
        }

        return memberRepository.findAll().stream()
                .map(MemberInfo::from)
                .toList();
    }

    // 관리자용
    @Transactional
    public void grantingMember(String adminNickname, Long memberId) {
        if(!isAdminMember(adminNickname)){
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
        if(!isAdminMember(adminNickname)){
            throw CustomException.of(MemberErrorCode.NO_AUTHENTICATION);
        }

        memberRepository.deleteById(memberId);
    }

    @Transactional(readOnly = true)
    public MemberInfo loginMember(String nickname){
        return MemberInfo.from(memberRepository.findByNickname(nickname));
    }

    @Transactional(readOnly = true)
    public boolean isExistMember(String nickname){
        return memberRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public boolean isAdminMember(String nickname){
        if(!isExistMember(nickname)){
            throw CustomException.of(MemberErrorCode.NOT_FOUND);
        }

        return memberRepository.findByNickname(nickname).adminCheck();
    }
}
