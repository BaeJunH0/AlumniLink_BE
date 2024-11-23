package com.sparksInTheStep.webBoard.auth.application;

import com.sparksInTheStep.webBoard.auth.application.dto.MemberCommand;
import com.sparksInTheStep.webBoard.auth.domain.Member;
import com.sparksInTheStep.webBoard.auth.persistent.MemberRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void makeNewUser(MemberCommand memberCommand){
        if(isExistMember(memberCommand.nickname())){
            throw new DuplicateRequestException("이미 존재하는 닉네임 입니다");
        }

        memberRepository.save(Member.of(memberCommand.nickname(), memberCommand.password()));
    }

    public boolean memberCheck(MemberCommand memberCommand){
        if(!isExistMember(memberCommand.nickname())){
            Member savedMember = memberRepository.findByNickname(memberCommand.nickname());
            Member checkMember = Member.of(memberCommand.nickname(), memberCommand.password());

            return savedMember.passCheck(checkMember.getPassword());
        }
        return false;
    }

    public boolean isExistMember(String nickname){
        return memberRepository.existsByNickname(nickname);
    }
}
