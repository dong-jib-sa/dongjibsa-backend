package com.djs.dongjibsabackend.global.jwt;

import com.djs.dongjibsabackend.repository.MemberRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Getter
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${jwt.secret}") private String secretKey;
    @Value("${jwt.access.expiration}") private Long accessTokenExpirationPeriod;
    @Value("${jwt.refresh.expiration}") private Long refreshTokenExpirationPeriod;
    @Value("${jwt.access.header}") private String accessHeader;
    @Value("${jwt.refresh.header}") private String refreshHeader;

    /**
     * Claim = {email: "id@address"}
     */
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";

    private final MemberRepository memberRepository;

    public String createAccessToken (String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                   .setSubject(ACCESS_TOKEN_SUBJECT)
                   .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                   .setClaims(claims) // {"email": "twohj1218@naver.com"}
                   .setExpiration(Date.from(Instant.now().plus(accessTokenExpirationPeriod, ChronoUnit.HOURS)))
                   .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                   .compact();
    }

    public String createRefreshToken() {
        return Jwts.builder()
                   .setSubject(REFRESH_TOKEN_SUBJECT)
                   .setExpiration(Date.from(Instant.now().plus(refreshTokenExpirationPeriod, ChronoUnit.HOURS)))
                   .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()))
                   .compact();
    }

    public void sendAccessTokenWithHeader (HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, accessToken); // { Authorization: accessToken }
    }

    public void sendAccessAndRefreshTokenWithHeader (HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader(accessHeader, accessToken);
        response.setHeader(refreshHeader, refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    /**
     * Request로부터 Access-Token 추출
     */
    public Optional<String> extractAccessToken (HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                       .filter(refreshToken -> refreshToken.startsWith(BEARER))
                       .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     * Request로부터 Refresh-Token 추출
     */
    public Optional<String> extractRefreshToken (HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                       .filter(refreshToken -> refreshToken.startsWith(BEARER))
                       .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    /**
     *
     *
     */
    public Optional<String> extreactEmail (String accessToken) {
        try {
            return Optional.ofNullable(
                Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build()
                    .parseClaimsJws(accessToken).getBody().getSubject());
        } catch (Exception e) {
            log.error("Access Token is not valid.");
            return Optional.empty();
        }
    }

    public String validateTokenAndGetEmail (String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey.getBytes())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public void updateRefreshToken (String phoneNumber, String refreshToken) {
        memberRepository.findByPhoneNumber(phoneNumber)
                        .ifPresentOrElse(
                userEntity -> userEntity.updateRefreshToken(refreshToken),
                () -> new Exception("User Not Found.")
                            );
    }

    public boolean verifyToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build().isSigned(token);
            return true;
        } catch (Exception e){
            log.error("Invalid Token: {}", e.getMessage());
            return false;
        }
    }
}
