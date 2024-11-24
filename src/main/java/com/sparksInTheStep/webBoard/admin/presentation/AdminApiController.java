package com.sparksInTheStep.webBoard.admin.presentation;

import com.sparksInTheStep.webBoard.auth.application.MemberService;
import com.sparksInTheStep.webBoard.auth.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.auth.presentation.dto.MemberResponse;
import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class AdminApiController {
    public final MemberService memberService;

    @GetMapping
    public ResponseEntity<?> readAllUsers(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @PageableDefault Pageable pageable
    ) {
        return new ResponseEntity<>(
                memberService.readAllMembers(memberInfo.nickname(),pageable)
                        .map(MemberResponse.Special::from),
                HttpStatus.OK
        );
    }

    @PatchMapping("{userId}")
    public ResponseEntity<?> makeUserAdmin(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @PathVariable Long userId
    ) {
        memberService.grantingMember(memberInfo.nickname(), userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(
            @AuthorizedUser MemberInfo.Default memberInfo,
            @PathVariable Long userId
    ) {
        memberService.deleteMember(memberInfo.nickname(), userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
