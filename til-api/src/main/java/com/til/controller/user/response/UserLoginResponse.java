package com.til.controller.user.response;

import com.til.domain.auth.dto.AuthTokenDto;
import com.til.domain.auth.dto.AuthUserInfoDto;
import com.til.domain.user.dto.UserPublicInfoDto;

public record UserLoginResponse(
                                UserPublicInfoDto user,
                                AuthTokenDto token
) {

    public static UserLoginResponse of(AuthUserInfoDto userInfoDto, AuthTokenDto authTokenDto) {
        return new UserLoginResponse(UserPublicInfoDto.of(userInfoDto), authTokenDto);
    }
}
