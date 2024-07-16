package com.til.domain.auth.provider;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.til.domain.auth.enums.AuthErrorCode;
import com.til.domain.auth.exception.TokenInvalidException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
class TokenProviderTest {

    @InjectMocks
    private TokenProvider tokenProvider;

    private static final String SECRET_KEY = "test1234".repeat(10);

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenProvider, "SECRET_KEY", SECRET_KEY);
        ReflectionTestUtils.setField(tokenProvider, "SIGN_KEY", Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)));
    }

    @Test
    void 유효기간이_지난_토큰을_검증하면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> tokenProvider.validateToken(createExpiredToken()))
            .isInstanceOf(TokenInvalidException.class)
            .extracting(error -> ((TokenInvalidException) error).getErrorCode())
            .isEqualTo(AuthErrorCode.EXPIRED_TOKEN);
    }

    @Test
    void 시크릿키가_문제있는_토큰을_검증하면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> tokenProvider.validateToken(createInvalidToken()))
            .isInstanceOf(TokenInvalidException.class)
            .extracting(error -> ((TokenInvalidException) error).getErrorCode())
            .isEqualTo(AuthErrorCode.INVALID_TOKEN);
    }

    @Test
    void 유효기간이_지나지_않고_시크릿키가_문제없는_토큰을_검증하면_예외를_던지지_않는다() {
        // given & when & then
        assertDoesNotThrow(() -> tokenProvider.validateToken(createValidToken()));
    }

    private String createExpiredToken() {
        return Jwts.builder()
            .subject("test@til.com")
            .claims(null)
            .expiration(new Date(System.currentTimeMillis() - 1000000))
            .signWith(getSignKey())
            .compact();
    }

    private String createInvalidToken() {
        return Jwts.builder()
            .subject("test@til.com")
            .claims(null)
            .expiration(new Date(System.currentTimeMillis() + 1000000))
            .signWith(getWrongSignKey())
            .compact();
    }

    private String createValidToken() {
        return Jwts.builder()
            .subject("test@til.com")
            .claims(Map.of("nickname", "test", "role", "USER", "tokenType", "ACCESS"))
            .expiration(new Date(System.currentTimeMillis() + 1000000))
            .signWith(getSignKey())
            .compact();
    }

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    private SecretKey getWrongSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode("wrong".repeat(10)));
    }
}
