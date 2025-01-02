package com.sparksInTheStep.webBoard.member.persistent;

import com.sparksInTheStep.webBoard.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findAll(Pageable pageable);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    Member findByNickname(String nickname);

    Member findByEmail(String email);
}
