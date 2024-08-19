package com.til.domain.auth.dto;

import java.util.Map;

import com.til.domain.auth.enums.TokenType;
import com.til.domain.user.model.Role;

import io.jsonwebtoken.Claims;
import lombok.Builder;

@Builder
public record AuthUserInfoDto(
                              Long id,
                              String nickname,
                              Role role
) {

    public String getSubject() {
        return this.id.toString();
    }

    public Map<String, Object> toClaims(TokenType tokenType) {
        return Map.of(
            "nickname", this.nickname,
            "role", this.role,
            "tokenType", tokenType.name()
        );
    }

    public static AuthUserInfoDto of(Claims claims) {
        String id = claims.getSubject();
        String nickname = claims.get("nickname").toString();
        Role role = Role.valueOf(claims.get("role").toString());

        return AuthUserInfoDto.builder()
            .id(Long.parseLong(id))
            .nickname(nickname)
            .role(role)
            .build();
    }

    public static AuthUserInfoDto of(Long id, String nickname, Role role) {
        return AuthUserInfoDto.builder()
            .id(id)
            .nickname(nickname)
            .role(role)
            .build();
    }

    public static boolean isGuest(AuthUserInfoDto authUserInfoDto) {
        return authUserInfoDto == null;
    }
}
