package com.til.domain.user.enums;

import org.springframework.http.HttpStatus;

import com.til.domain.common.enums.ErrorCode;

import lombok.Getter;

@Getter
public enum UserErrorCode implements ErrorCode {
	ALREADY_EXISTS_NICKNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다."),
	ALREADY_EXISTS_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
	NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다.");

	private final HttpStatus status;
	private final String message;

	UserErrorCode(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
}
