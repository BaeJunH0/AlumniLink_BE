package com.sparksInTheStep.webBoard.auth.persistent;

import com.sparksInTheStep.webBoard.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByNickname(String nickname);

    public User findByNickname(String nickname);
}
