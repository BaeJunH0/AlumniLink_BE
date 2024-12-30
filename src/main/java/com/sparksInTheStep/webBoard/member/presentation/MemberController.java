package com.sparksInTheStep.webBoard.member.presentation;

import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/members")
public class MemberController implements MemberApiSpec {
    @GetMapping("/my/nickname")
    public ResponseEntity<?> getNickname(
            @AuthorizedUser MemberInfo.Default memberInfo
    ) {
        return new ResponseEntity<>(memberInfo.nickname(), HttpStatus.OK);
    }
}
