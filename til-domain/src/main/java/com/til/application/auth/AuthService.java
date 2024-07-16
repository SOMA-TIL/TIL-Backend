package com.til.application.auth;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.til.config.db.RedisManager;
import com.til.domain.auth.dto.AuthTokenDto;
import com.til.domain.auth.dto.AuthUserInfoDto;
import com.til.domain.auth.enums.AuthErrorCode;
import com.til.domain.auth.enums.TokenType;
import com.til.domain.auth.exception.TokenInvalidException;
import com.til.domain.auth.provider.TokenProvider;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final TokenProvider tokenProvider;
    private final RedisManager redisManager;

    @Value("${jwt.access.expiration}")
    private Long ACCESS_EXPIRE_DURATION;

    @Value("${jwt.refresh.expiration}")
    private Long REFRESH_EXPIRE_DURATION;

    private static final String BEARER_TYPE = "Bearer";

    public AuthTokenDto createToken(AuthUserInfoDto authUserInfoDto) {
        String accessToken = generateToken(authUserInfoDto, TokenType.ACCESS);
        String refreshToken = generateToken(authUserInfoDto, TokenType.REFRESH);
        redisManager.setData(TokenType.REFRESH.name() + "_TOKEN:" + authUserInfoDto.email(), refreshToken,
            REFRESH_EXPIRE_DURATION);

        return AuthTokenDto.of(accessToken, refreshToken);
    }

    public AuthTokenDto reissueToken(String bearerToken) {
        String refreshToken = resolveToken(bearerToken);
        tokenProvider.validateToken(refreshToken);

        Claims claims = tokenProvider.parseClaims(refreshToken);
        AuthUserInfoDto authUserInfoDto = AuthUserInfoDto.of(claims);

        validateRefreshToken(authUserInfoDto.email(), refreshToken);

        return createToken(authUserInfoDto);
    }

    public void validateRefreshToken(String key, String refreshToken) {
        String redisToken = redisManager.getData(TokenType.REFRESH.name() + "_TOKEN:" + key);
        if (!Objects.equals(refreshToken, redisToken)) {
            throw new TokenInvalidException(AuthErrorCode.INVALID_TOKEN);
        }
    }

    private String generateToken(AuthUserInfoDto authUserInfoDto, TokenType tokenType) {
        String subject = authUserInfoDto.getSubject();
        Map<String, Object> claims = authUserInfoDto.toClaims(tokenType);
        Long expireDuration = getExpireDuration(tokenType);

        return tokenProvider.generateToken(subject, claims, expireDuration);
    }

    private String resolveToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(BEARER_TYPE.length() + 1);
        }
        return null;
    }

    private Long getExpireDuration(TokenType tokenType) {
        return tokenType == TokenType.ACCESS ? ACCESS_EXPIRE_DURATION : REFRESH_EXPIRE_DURATION;
    }
}
