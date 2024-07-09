package com.til.controller.user.response;

import com.til.domain.auth.dto.AuthTokenDto;

public record UserTokenResponse(
	String accessToken,
	String refreshToken
) {
	public static UserTokenResponse of(AuthTokenDto authTokenDto) {
		return new UserTokenResponse(authTokenDto.accessToken(), authTokenDto.refreshToken());
	}
}
