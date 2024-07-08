package com.til.domain.auth.provider;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.til.domain.auth.exception.TokenInvalidException;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@ExtendWith(MockitoExtension.class)
class TokenProviderTest {
	@InjectMocks
	private TokenProvider tokenProvider;

	private String secretKey = "test1234".repeat(10);

	@BeforeEach
	void setUp() {
		ReflectionTestUtils.setField(tokenProvider, "secretKey", secretKey);
		tokenProvider.afterPropertiesSet();
	}

	@Test
	void 유효기간이_지난_토큰을_검증하면_예외를_던진다() {
		// given
		String token = createExpiredToken();

		// when
		Throwable throwable = catchThrowable(() -> tokenProvider.validateToken(token));

		// then
		assertThat(throwable).isInstanceOf(TokenInvalidException.class);
	}

	@Test
	void 시크릿키가_문제있는_토큰을_검증하면_예외를_던진다() {
		// given
		String token = createInvalidToken();

		// when
		Throwable throwable = catchThrowable(() -> tokenProvider.validateToken(token));

		// then
		assertThat(throwable).isInstanceOf(TokenInvalidException.class);
	}

	@Test
	void 유효기간이_지나지_않고_시크릿키가_문제없는_토큰을_검증하면_예외를_던지지_않는다() {
		// given
		String token = createValidToken();

		// when
		Throwable throwable = catchThrowable(() -> tokenProvider.validateToken(token));

		// then
		assertThat(throwable).isNull();
	}

	private String createExpiredToken() {
		return Jwts.builder()
			.subject("test@til.com")
			.claims(null)
			.expiration(new Date(System.currentTimeMillis() - 60000))
			.signWith(getSecretKey())
			.compact();
	}

	private String createInvalidToken() {
		return Jwts.builder()
			.subject("test@til.com")
			.claims(null)
			.expiration(new Date(System.currentTimeMillis() + 1000000))
			.signWith(getWrongSecretKey())
			.compact();
	}

	private String createValidToken() {
		return Jwts.builder()
			.subject("test@til.com")
			.claims(Map.of("nickname", "test", "role", "USER", "tokenType", "ACCESS"))
			.expiration(new Date(System.currentTimeMillis() + 1000000))
			.signWith(getSecretKey())
			.compact();
	}

	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}

	private SecretKey getWrongSecretKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode("wrong".repeat(10)));
	}
}
