package com.sparksInTheStep.webBoard.admin.presentation;

import com.sparksInTheStep.webBoard.auth.application.MemberService;
import com.sparksInTheStep.webBoard.auth.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.auth.persistent.MemberRepository;
import com.sparksInTheStep.webBoard.auth.presentation.dto.MemberRequest;
import com.sparksInTheStep.webBoard.auth.presentation.dto.MemberResponse;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class AdminApiController {
    public final MemberService memberService;

    @GetMapping
    public ResponseEntity<?> readAllUsers(@AuthorizedUser MemberInfo memberInfo) {
        return new ResponseEntity<>(
                memberService.readAllMembers(memberInfo.nickname()).stream()
                        .map(MemberResponse::from)
                        .toList(),
                HttpStatus.OK
        );
    }

    @PatchMapping("{userId}")
    public ResponseEntity<?> makeUserAdmin(
            @AuthorizedUser MemberInfo memberInfo,
            @PathVariable Long userId
    ) {
        memberService.grantingMember(memberInfo.nickname(), userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(
            @AuthorizedUser MemberInfo memberInfo,
            @PathVariable Long userId
    ) {
        memberService.deleteMember(memberInfo.nickname(), userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
