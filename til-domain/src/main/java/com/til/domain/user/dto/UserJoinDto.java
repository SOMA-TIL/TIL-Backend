package com.til.domain.user.dto;

import com.til.domain.user.model.Platform;
import com.til.domain.user.model.Role;
import com.til.domain.user.model.Status;
import com.til.domain.user.model.User;

import lombok.Builder;

@Builder
public record UserJoinDto(
	String email,
	String password,
	String nickname
) {
	public User toEntity() {
		return User.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.platform(Platform.TIL)
			.role(Role.USER)
			.status(Status.ACTIVE)
			.build();
	}
}
