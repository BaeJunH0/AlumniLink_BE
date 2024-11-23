package com.sparksInTheStep.webBoard.auth.persistent;

import com.sparksInTheStep.webBoard.auth.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByNickname(String nickname);

    Member findByNickname(String nickname);
}
