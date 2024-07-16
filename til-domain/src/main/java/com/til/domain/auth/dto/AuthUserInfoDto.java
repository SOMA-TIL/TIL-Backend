package com.til.domain.auth.dto;

import java.util.Map;

import com.til.domain.auth.enums.TokenType;
import com.til.domain.user.model.Role;

import io.jsonwebtoken.Claims;
import lombok.Builder;

@Builder
public record AuthUserInfoDto(
                              String email,
                              String nickname,
                              Role role
) {

    public String getSubject() {
        return this.email;
    }

    public Map<String, Object> toClaims(TokenType tokenType) {
        return Map.of(
            "nickname", this.nickname,
            "role", this.role,
            "tokenType", tokenType.name()
        );
    }

    public static AuthUserInfoDto of(Claims claims) {
        String email = claims.getSubject().toString();
        String nickname = claims.get("nickname").toString();
        Role role = Role.valueOf(claims.get("role").toString());

        return AuthUserInfoDto.builder()
            .email(email)
            .nickname(nickname)
            .role(role)
            .build();
    }

    public static AuthUserInfoDto of(String email, String nickname, Role role) {
        return AuthUserInfoDto.builder()
            .email(email)
            .nickname(nickname)
            .role(role)
            .build();
    }
}
