package com.sparksInTheStep.webBoard.auth.presentation;

import com.sparksInTheStep.webBoard.auth.application.UserService;
import com.sparksInTheStep.webBoard.auth.application.dto.UserCommand;
import com.sparksInTheStep.webBoard.auth.presentation.dto.UserRequest;
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
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest){
        if(userService.passCheck(UserCommand.from(userRequest))){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String accessToken = JwtUtil.makeAccessToken(userRequest.nickname());
        return new ResponseEntity<>(Token.of(accessToken), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest){
        userService.makeNewUser(UserCommand.from(userRequest));

        String accessToken = JwtUtil.makeAccessToken(userRequest.nickname());
        return new ResponseEntity<>(Token.of(accessToken), HttpStatus.CREATED);
    }
}
