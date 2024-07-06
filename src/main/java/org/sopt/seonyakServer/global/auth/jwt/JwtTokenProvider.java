package org.sopt.seonyakServer.global.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String MEMBER_ID = "memberId";

    @Value("${jwt.access-token-expire-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${jwt.secret}")
    private String JWT_SECRET;

    @PostConstruct
    protected void init() {
        //base64 라이브러리에서 encodeToString을 이용해서 byte[] 형식을 String 형식으로 변환
        JWT_SECRET = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private String getTokenFromHeader(final String token) {
        if (!StringUtils.hasText(token)) {
            throw new CustomException(ErrorType.UN_LOGIN_ERROR);
        } else if (StringUtils.hasText(token) && !token.startsWith("Bearer ")) {
            throw new CustomException(ErrorType.BEARER_LOST_ERROR);
        }

        return token.substring("Bearer ".length());
    }

    public String issueAccessToken(final Authentication authentication) {
        return generateToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateToken(
            Authentication authentication,
            Long tokenExpirationTime
    ) {
        final Date now = new Date();

        final Claims claims = Jwts.claims()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenExpirationTime)); // 만료 시간 설정

        claims.put(MEMBER_ID, authentication.getPrincipal());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // Header
                .setClaims(claims) // Claim
                .signWith(getSigningKey()) // Signature
                .compact();
    }

    private SecretKey getSigningKey() {
        String encodedKey = Base64.getEncoder().encodeToString(JWT_SECRET.getBytes()); // SecretKey를 통해 서명 생성

        // 일반적으로 HMAC (Hash-based Message Authentication Code) 알고리즘을 사용
        return Keys.hmacShaKeyFor(encodedKey.getBytes());
    }

    public JwtValidationType validateToken(String token) {
        try {
            final Claims claims = getBody(getTokenFromHeader(token));
            return JwtValidationType.VALID_JWT;
        } catch (MalformedJwtException ex) {
            return JwtValidationType.INVALID_JWT_TOKEN;
        } catch (ExpiredJwtException ex) {
            return JwtValidationType.EXPIRED_JWT_TOKEN;
        } catch (UnsupportedJwtException ex) {
            return JwtValidationType.UNSUPPORTED_JWT_TOKEN;
        } catch (IllegalArgumentException ex) {
            return JwtValidationType.EMPTY_JWT;
        }
    }

    private Claims getBody(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getMemberIdFromJwt(String token) {
        Claims claims = getBody(getTokenFromHeader(token));

        return Long.valueOf(claims.get(MEMBER_ID).toString());
    }
}
