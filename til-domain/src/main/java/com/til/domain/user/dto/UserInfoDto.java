package com.til.domain.user.dto;

import com.til.domain.user.model.User;

import lombok.Builder;

@Builder
public record UserInfoDto(
                          Long id,
                          String email,
                          String nickname
) {

    public static UserInfoDto of(User user) {
        return UserInfoDto.builder()
            .id(user.getId())
            .email(user.getEmail())
            .nickname(user.getNickname())
            .build();
    }
}
