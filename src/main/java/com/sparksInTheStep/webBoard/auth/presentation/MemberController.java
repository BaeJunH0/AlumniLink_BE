package com.sparksInTheStep.webBoard.auth.presentation;

import com.sparksInTheStep.webBoard.auth.application.MemberService;
import com.sparksInTheStep.webBoard.auth.application.dto.MemberCommand;
import com.sparksInTheStep.webBoard.auth.presentation.dto.MemberRequest;
import com.sparksInTheStep.webBoard.auth.util.JwtUtil;
import com.sparksInTheStep.webBoard.auth.util.Token;
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
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberRequest memberRequest){
        if(memberService.memberCheck(MemberCommand.from(memberRequest))){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String accessToken = JwtUtil.makeAccessToken(memberRequest.nickname());
        return new ResponseEntity<>(Token.of(accessToken), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody MemberRequest memberRequest){
        memberService.makeNewUser(MemberCommand.from(memberRequest));

        String accessToken = JwtUtil.makeAccessToken(memberRequest.nickname());
        return new ResponseEntity<>(Token.of(accessToken), HttpStatus.CREATED);
    }
}
