package com.til.domain.auth.dto;

import lombok.Builder;

@Builder
public record AuthTokenDto(
                           String accessToken,
                           String refreshToken
) {

    public static AuthTokenDto of(String accessToken, String refreshToken) {
        return new AuthTokenDto(accessToken, refreshToken);
    }
}
