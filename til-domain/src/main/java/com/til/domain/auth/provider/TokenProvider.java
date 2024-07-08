package com.til.domain.auth.provider;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.til.domain.auth.enums.AuthErrorCode;
import com.til.domain.auth.exception.TokenInvalidException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenProvider implements InitializingBean {
	@Value("${jwt.secret}")
	private String secretKey;

	private SecretKey key;

	@Override
	public void afterPropertiesSet() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(String subject, Map<String, Object> claims, Long expireDuration) {
		Date tokenExpireTime = makeExpireTime(expireDuration);

		return Jwts.builder()
			.subject(subject)
			.claims(claims)
			.expiration(tokenExpireTime)
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public void validateToken(String token) {
		try {
			Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
		} catch (MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
			throw new TokenInvalidException(AuthErrorCode.INVALID_TOKEN);
		} catch (ExpiredJwtException e) {
			throw new TokenInvalidException(AuthErrorCode.EXPIRED_TOKEN);
		}
	}

	public Claims parseClaims(String token) {
		return Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}

	private Date makeExpireTime(Long expireDuration) {
		return new Date(System.currentTimeMillis() + expireDuration);
	}
}
