package com.til.domain.user.dto;

import com.til.domain.auth.dto.AuthUserInfoDto;
import com.til.domain.user.model.User;

import lombok.Builder;

@Builder
public record UserPublicInfoDto(
                                String nickname
) {

    public static UserPublicInfoDto of(User user) {
        return UserPublicInfoDto.builder()
            .nickname(user.getNickname())
            .build();
    }

    public static UserPublicInfoDto of(AuthUserInfoDto authUserInfo) {
        return UserPublicInfoDto.builder()
            .nickname(authUserInfo.nickname())
            .build();
    }
}
