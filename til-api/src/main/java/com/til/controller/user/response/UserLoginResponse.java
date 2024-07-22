package com.til.controller.user.response;

import com.til.domain.auth.dto.AuthTokenDto;

public record UserLoginResponse(
                                AuthTokenDto token
) {

    public static UserLoginResponse of(AuthTokenDto authTokenDto) {
        return new UserLoginResponse(authTokenDto);
    }
}
