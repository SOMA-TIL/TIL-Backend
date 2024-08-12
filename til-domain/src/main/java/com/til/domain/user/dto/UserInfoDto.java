package com.til.domain.user.dto;

import com.til.domain.user.model.Role;

import io.jsonwebtoken.Claims;
import lombok.Builder;

@Builder
public record UserInfoDto(
                          Long id,
                          String nickname,
                          Role role
) {

    public static UserInfoDto of(Long id, String nickname, Role role) {
        return UserInfoDto.builder()
            .id(id)
            .nickname(nickname)
            .role(role)
            .build();
    }

    public static UserInfoDto of(Claims claims) {
        return UserInfoDto.builder()
            .id(Long.valueOf(claims.getSubject()))
            .nickname(claims.get("nickname").toString())
            .role(Role.valueOf(claims.get("role").toString()))
            .build();
    }
}
