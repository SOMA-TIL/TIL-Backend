package com.til.application.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.til.domain.auth.dto.AuthTokenDto;
import com.til.domain.auth.dto.AuthUserInfoDto;
import com.til.domain.auth.enums.TokenType;
import com.til.domain.auth.provider.TokenProvider;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {
	private final TokenProvider tokenProvider;

	@Value("${jwt.access.expiration}")
	private Long ACCESS_EXPIRE_DURATION;

	@Value("${jwt.refresh.expiration}")
	private Long REFRESH_EXPIRE_DURATION;
	private static final String BEARER_TYPE = "Bearer";

	public AuthTokenDto createToken(AuthUserInfoDto authUserInfoDto) {
		String accessToken = generateToken(authUserInfoDto, TokenType.ACCESS);
		String refreshToken = generateToken(authUserInfoDto, TokenType.REFRESH);

		// TODO : Redis에 refresh token 저장

		return AuthTokenDto.of(accessToken, refreshToken);
	}

	public AuthTokenDto reissueToken(String bearerToken) {
		String refreshToken = resolveToken(bearerToken);
		tokenProvider.validateToken(refreshToken);

		// TODO : Redis에 저장된 refresh token과 비교

		Claims claims = tokenProvider.parseClaims(refreshToken);
		AuthUserInfoDto authUserInfoDto = AuthUserInfoDto.of(claims);

		return createToken(authUserInfoDto);
	}

	private String generateToken(AuthUserInfoDto authUserInfoDto, TokenType tokenType) {
		String subject = authUserInfoDto.getSubject();
		Map<String, Object> claims = authUserInfoDto.toClaims(tokenType);
		Long expireDuration = getExpireDuration(tokenType);

		return tokenProvider.generateToken(subject, claims, expireDuration);
	}

	private String resolveToken(String bearerToken) {
		if (StringUtils.hasText(bearerToken) & bearerToken.startsWith(BEARER_TYPE)) {
			return bearerToken.substring(BEARER_TYPE.length() + 1);
		}
		return null;
	}

	private Long getExpireDuration(TokenType tokenType) {
		return tokenType == TokenType.ACCESS ? ACCESS_EXPIRE_DURATION : REFRESH_EXPIRE_DURATION;
	}
}