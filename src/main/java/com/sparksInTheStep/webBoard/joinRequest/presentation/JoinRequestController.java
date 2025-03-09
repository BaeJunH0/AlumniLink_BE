package com.sparksInTheStep.webBoard.joinRequest.presentation;

import com.sparksInTheStep.webBoard.global.annotation.AuthorizedUser;
import com.sparksInTheStep.webBoard.joinRequest.application.JoinRequestFacade;
import com.sparksInTheStep.webBoard.joinRequest.application.JoinRequestService;
import com.sparksInTheStep.webBoard.joinRequest.application.dto.JoinRequestInfo;
import com.sparksInTheStep.webBoard.joinRequest.presentation.dto.JoinRequestResponse;
import com.sparksInTheStep.webBoard.member.application.dto.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/requests")
public class JoinRequestController {
    private final JoinRequestFacade joinRequestFacade;

    // 내가 리더인 프로젝트에 온 요청들 조회
    @GetMapping("/my/received")
    public ResponseEntity<?> findReceivedRequests(
            @AuthorizedUser MemberInfo memberInfo
    ) {
        List<Map<Object, Object>> projectJoinRequests =
                joinRequestFacade.getProjectJoinRequests(memberInfo.nickname());

        return new ResponseEntity<>(projectJoinRequests, HttpStatus.OK);
    }

    // 내가 보낸 요청들 조회
    @GetMapping("/my/sent")
    public ResponseEntity<?> findSentRequests(
            @AuthorizedUser MemberInfo memberInfo
    ) {
        List<JoinRequestInfo> memberJoinRequestInfos =
                joinRequestFacade.getMemberJoinRequests(memberInfo.id(), memberInfo.nickname());

        return new ResponseEntity<>(
                memberJoinRequestInfos.stream().map(JoinRequestResponse::of).toList(),
                HttpStatus.OK
        );
    }

    // 요청 보내기
    @PostMapping("/{projectId}")
    public ResponseEntity<String> applyForProject(
            @AuthorizedUser MemberInfo memberInfo,
            @PathVariable Long projectId
    ) {
        joinRequestFacade.applyToProject(memberInfo.nickname(), projectId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 요청 수락 or 거절
    @PostMapping("/choice/{projectId}/{memberName}")
    public ResponseEntity<String> approveJoinRequest(
            @AuthorizedUser MemberInfo memberInfo,
            @PathVariable Long projectId,
            @PathVariable String memberName,
            @RequestParam boolean tf
    ) {
        joinRequestFacade.approveOrDeny(memberInfo.nickname(), projectId, memberName, tf);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
