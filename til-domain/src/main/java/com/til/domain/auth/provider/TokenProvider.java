package com.til.domain.auth.provider;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.til.domain.auth.enums.AuthErrorCode;
import com.til.domain.auth.exception.TokenInvalidException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenProvider {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private SecretKey SIGN_KEY;

    @PostConstruct
    public void settingSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        this.SIGN_KEY = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String subject, Map<String, Object> claims, Long expireDuration) {
        Date tokenExpireTime = makeExpireTime(expireDuration);

        return Jwts.builder()
            .subject(subject)
            .claims(claims)
            .expiration(tokenExpireTime)
            .signWith(SIGN_KEY)
            .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parser().verifyWith(SIGN_KEY).build().parseSignedClaims(token);
        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new TokenInvalidException(AuthErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new TokenInvalidException(AuthErrorCode.EXPIRED_TOKEN);
        }
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
            .verifyWith(SIGN_KEY)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    private Date makeExpireTime(Long expireDuration) {
        return new Date(System.currentTimeMillis() + expireDuration);
    }
}
