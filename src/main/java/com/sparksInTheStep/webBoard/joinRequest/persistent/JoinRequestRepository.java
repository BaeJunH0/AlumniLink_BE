package com.sparksInTheStep.webBoard.joinRequest.persistent;

import com.sparksInTheStep.webBoard.joinRequest.domain.JoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    List<JoinRequest> findProjectMemberRequestByMember_Nickname(String nickname);
}
