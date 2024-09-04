package com.til.controller.user.response;

import com.til.domain.user.dto.UserInfoDto;

import lombok.Builder;

@Builder
public record UserInfoResponse(
                               UserInfoDto userInfo
) {

    public static UserInfoResponse of(UserInfoDto userInfoDto) {
        return UserInfoResponse.builder()
            .userInfo(userInfoDto)
            .build();
    }
}
