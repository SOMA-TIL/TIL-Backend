package com.til.controller.auth.response;

import com.til.domain.auth.dto.AuthTokenDto;

public record AuthTokenResponse(
                                AuthTokenDto token
) {

    public static AuthTokenResponse of(AuthTokenDto token) {
        return new AuthTokenResponse(token);
    }
}
