package com.sparksInTheStep.webBoard.joinRequest.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JoinRequestService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final long REQUEST_TTL_SEC = 259200;

    private String getMemberRequestKey(Long memberId) {
        return "member:join:request::" + memberId;
    }

    private String getProjectRequestKey(Long projectId) {
        return "project:join:request:" + projectId;
    }

    // 프로젝트 가입 신청
    public void applyForProject(Long memberId, Long projectId) {
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        long ttl = System.currentTimeMillis() + (REQUEST_TTL_SEC * 1000);

        // 사용자 기준 요청 저장
        zSetOps.add(getMemberRequestKey(memberId), projectId.toString(), ttl);

        // 프로젝트 기준 요청 저장
        redisTemplate.opsForHash().put(getProjectRequestKey(projectId), memberId.toString(), "PENDING");
    }

    // 사용자 기준 프로젝트 조회 ( 내가 신청한 프로젝트 조회 )
    public Set<Object> getMemberJoinRequest(Long memberId) {
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        long now = System.currentTimeMillis();

        // 만료 요청 제거
        zSetOps.removeRangeByScore(getMemberRequestKey(memberId), 0, now);

        // 최신 요청 목록 조회
        return zSetOps.range(getMemberRequestKey(memberId), 0, -1);
    }

    // 프로젝트 기준 사용자 조회 ( 리더가 자신에게 온 요청 조회 )
    public Map<Object, Object> getProjectJoinRequest(Long projectId) {
        return redisTemplate.opsForHash().entries(getProjectRequestKey(projectId));
    }

    // 가입 요청 승인 및 거절
    public void removeRequest(Long memberId, Long projectId) {
        // 공통
        redisTemplate.opsForZSet().remove(getMemberRequestKey(memberId), projectId.toString());
        redisTemplate.opsForHash().delete(getProjectRequestKey(projectId), memberId.toString());
    }
}
