package com.sparksInTheStep.webBoard.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtUtil {
    @Value("${KEY}")
    private static String secretKey;

    @Value("${TOKEN_EXPIRE_LENGTH}")
    private static long validTime;

    /*
     * 토큰 생성 : Claim => nickname
     */
    public static String makeAccessToken(String nickname) {
        Long nowMillis = System.currentTimeMillis();
        Date now = new Date(System.currentTimeMillis());

        String accessToken = Jwts.builder()
                .claim("nickname", nickname)
                .issuedAt(now)
                .expiration(new Date(nowMillis + validTime))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();

        return accessToken;
    }
    /*
     * 토큰에서 클레임 ( nickname ) 추출
     */
    public static String getNicknameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("nickname", String.class);
        } catch(Exception e){
            return "null";
        }
    }
}
