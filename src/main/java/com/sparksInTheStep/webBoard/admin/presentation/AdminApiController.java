package com.sparksInTheStep.webBoard.admin.presentation;

import com.sparksInTheStep.webBoard.auth.token.JwtTokenProvider;
import com.sparksInTheStep.webBoard.auth.token.Token;
import com.sparksInTheStep.webBoard.member.application.MemberService;
import com.sparksInTheStep.webBoard.member.application.dto.MemberCommand;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import com.sparksInTheStep.webBoard.member.presentation.dto.MemberRequest;
import com.sparksInTheStep.webBoard.member.presentation.dto.MemberResponse;
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
    public final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<?> readAllMembers(
            @AuthorizedUser MemberInfo memberInfo,
            @PageableDefault Pageable pageable
    ) {
        return new ResponseEntity<>(
                memberService.readAllMembers(memberInfo.email(),pageable)
                        .map(MemberResponse.Special::from),
                HttpStatus.OK
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@RequestBody MemberRequest.Login memberRequest){
        if(!memberService.adminCheck(MemberCommand.from(memberRequest))){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String accessToken = jwtTokenProvider.makeAccessToken(
                memberRequest.email(),
                memberService.getMemberName(memberRequest.email())
        );
        String refreshToken = jwtTokenProvider.makeRefreshToken(
                memberRequest.email(),
                memberService.getMemberName(memberRequest.email())
        );

        return new ResponseEntity<>(Token.of(accessToken, refreshToken), HttpStatus.OK);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<?> makeMemberAdmin(
            @AuthorizedUser MemberInfo memberInfo,
            @PathVariable Long userId
    ) {
        memberService.grantingMember(memberInfo.email(), userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteMember(
            @AuthorizedUser MemberInfo memberInfo,
            @PathVariable Long userId
    ) {
        memberService.deleteMember(memberInfo.email(), userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
