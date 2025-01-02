package com.sparksInTheStep.webBoard.member.presentation;

import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import com.sparksInTheStep.webBoard.member.application.MemberService;
import com.sparksInTheStep.webBoard.member.application.dto.MemberCommand;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.member.presentation.dto.MemberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController implements MemberApiSpec {
    private final MemberService memberService;

    @PutMapping("/my")
    public ResponseEntity<?> updateMember(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @RequestBody MemberRequest memberRequest
    ) {
        memberService.updateMember(memberInfo.email(), MemberCommand.from(memberRequest));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
