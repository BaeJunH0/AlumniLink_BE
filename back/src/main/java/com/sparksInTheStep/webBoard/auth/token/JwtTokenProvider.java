package com.sparksInTheStep.webBoard.auth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${key}")
    private String secretKey;

    @Value("${token-expire-length}")
    private long validTime;

    /*
     * 토큰 생성 : Claim => nickname
     */
    public String makeAccessToken(String nickname) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(System.currentTimeMillis());

        return Jwts.builder()
                .claim("nickname", nickname)
                .issuedAt(now)
                .expiration(new Date(nowMillis + validTime))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }
    /*
     * 토큰에서 클레임 ( nickname ) 추출
     */
    public String getNicknameFromToken(String token) {
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
