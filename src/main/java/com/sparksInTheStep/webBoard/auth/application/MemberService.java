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
    public boolean isExistMember(String nickname){
        return memberRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public MemberInfo loginMember(String nickname){
        return MemberInfo.from(memberRepository.findByNickname(nickname));
    }
}
