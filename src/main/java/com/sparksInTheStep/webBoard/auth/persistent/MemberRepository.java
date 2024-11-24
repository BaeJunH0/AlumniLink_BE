package com.sparksInTheStep.webBoard.auth.persistent;

import com.sparksInTheStep.webBoard.auth.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findAll(Pageable pageable);

    boolean existsByNickname(String nickname);

    Member findByNickname(String nickname);
}
