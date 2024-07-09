package com.til.domain.user.dto;

import com.til.domain.user.model.User;

import lombok.Builder;

@Builder
public record UserLoginDto(
	String email,
	String password
) {
	public User toEntity() {
		return User.builder()
			.email(email)
			.password(password)
			.build();
	}
}
