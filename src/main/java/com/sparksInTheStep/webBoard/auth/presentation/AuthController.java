package com.sparksInTheStep.webBoard.auth.presentation;

import com.sparksInTheStep.webBoard.member.application.MemberService;
import com.sparksInTheStep.webBoard.member.application.dto.MemberCommand;
import com.sparksInTheStep.webBoard.member.presentation.dto.MemberRequest;
import com.sparksInTheStep.webBoard.auth.token.JwtTokenProvider;
import com.sparksInTheStep.webBoard.auth.token.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController implements AuthApiSpec{
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberRequest.Login memberRequest){
        if(!memberService.memberCheck(MemberCommand.from(memberRequest))){
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody MemberRequest.Register memberRequest){
        memberService.makeNewUser(MemberCommand.from(memberRequest));

        String accessToken = jwtTokenProvider.makeAccessToken(
                memberRequest.email(),
                memberRequest.nickname()
        );
        String refreshToken = jwtTokenProvider.makeRefreshToken(
                memberRequest.email(),
                memberRequest.nickname()
        );

        return new ResponseEntity<>(Token.of(accessToken, refreshToken), HttpStatus.CREATED);
    }
}
