package com.sparksInTheStep.webBoard.member.presentation;

import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import com.sparksInTheStep.webBoard.member.application.MemberService;
import com.sparksInTheStep.webBoard.member.application.dto.MemberCommand;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.member.presentation.dto.MemberRequest;
import com.sparksInTheStep.webBoard.member.presentation.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController implements MemberApiSpec {
    private final MemberService memberService;

    @GetMapping("/{nickname}")
    public ResponseEntity<?> getMemberInformation(
        @PathVariable String nickname
    ) {
        MemberResponse.Default memberResponse = MemberResponse.Default.from(
                memberService.findMemberByNickname(nickname)
        );

        return new ResponseEntity<>(memberResponse, HttpStatus.OK);
    }

    @PutMapping("/my")
    public ResponseEntity<?> updateMember(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @RequestBody MemberRequest.Register memberRequest
    ) {
        memberService.updateMember(memberInfo.email(), MemberCommand.from(memberRequest));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
