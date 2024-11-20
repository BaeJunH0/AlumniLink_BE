package com.sparksInTheStep.webBoard.auth.application;

import com.sparksInTheStep.webBoard.auth.application.dto.UserCommand;
import com.sparksInTheStep.webBoard.auth.domain.User;
import com.sparksInTheStep.webBoard.auth.persistent.UserRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void makeNewUser(UserCommand userCommand){
        if(isExistUser(userCommand.nickname())){
            throw new DuplicateRequestException("이미 존재하는 닉네임 입니다");
        }

        userRepository.save(User.of(userCommand.nickname(), userCommand.password()));
    }

    public boolean passCheck(UserCommand userCommand){
        if(isExistUser(userCommand.nickname())){
            User savedUser = userRepository.findByNickname(userCommand.nickname());
            User checkUser = User.of(userCommand.nickname(), userCommand.password());

            return savedUser.passCheck(checkUser.getPassword());
        }
        return false;
    }

    public boolean isExistUser(String nickname){
        return userRepository.existsByNickname(nickname);
    }
}
