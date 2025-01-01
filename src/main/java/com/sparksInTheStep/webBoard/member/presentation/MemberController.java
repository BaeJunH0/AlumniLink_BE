package com.sparksInTheStep.webBoard.member.presentation;

import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import com.sparksInTheStep.webBoard.member.application.MemberService;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController implements MemberApiSpec {
    private final MemberService memberService;

    @GetMapping("/my/nickname")
    public ResponseEntity<?> getNickname(
            @AuthorizedUser MemberInfo.Default memberInfo
    ) {
        return new ResponseEntity<>(memberInfo.nickname(), HttpStatus.OK);
    }

    @PatchMapping("/my/employed")
    public ResponseEntity<?> updateEmployed(
            @AuthorizedUser MemberInfo.Default memberInfo
    ) {
        memberService.updateEmployed(memberInfo.nickname());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
