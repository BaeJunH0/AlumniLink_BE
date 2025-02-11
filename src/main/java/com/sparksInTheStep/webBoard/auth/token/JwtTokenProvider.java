package com.sparksInTheStep.webBoard.auth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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
     * 액세스 토큰 생성 : Claim => email, nickname, "refresh"
     */
    public String makeAccessToken(String email, String nickname) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(System.currentTimeMillis());

        return Jwts.builder()
                .claim("email", email)
                .claim("nickname", nickname)
                .claim("type", "access")
                .issuedAt(now)
                .expiration(new Date(nowMillis + validTime)) // 1시간
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    /*
     * 리프레시 토큰 생성 : Claim => email, nickname, "refresh"
     */
    public String makeRefreshToken(String email, String nickname) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(System.currentTimeMillis());

        return Jwts.builder()
                .claim("email", email)
                .claim("nickname", nickname)
                .claim("type", "refresh")
                .issuedAt(now)
                .expiration(new Date(nowMillis + validTime * 168)) // 7일
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    /*
     * 토큰에서 클레임 ( Email ) 추출
     */
    public String getEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("email", String.class);
        } catch(Exception e){
            return "null";
        }
    }

    /*
     * 토큰에서 클레임 ( Nickname ) 추출
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

    /*
     *  토큰에서 클레임 ( Type ) 추출
     */
    public String getTypeFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("type", String.class);
        } catch(Exception e){
            return "null";
        }
    }

    /*
     * 토큰의 유효 시간 확인
     */
    public boolean isTokenExpired(String token) {
        try {
            // 토큰을 파싱하여 Claims 객체를 얻음
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // 만료 시간 가져오기
            Date expiration = claims.getExpiration();

            // 현재 시간과 만료 시간을 비교하여 만료 여부를 판단
            return expiration.before(new Date());
        } catch (SignatureException e) {
            // 유효하지 않은 서명일 경우 예외 처리
            return true;
        }
    }
}
