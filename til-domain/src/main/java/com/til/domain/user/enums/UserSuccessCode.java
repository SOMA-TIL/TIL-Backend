package com.til.domain.user.enums;

import com.til.domain.common.enums.SuccessCode;

import lombok.Getter;

@Getter
public enum UserSuccessCode implements SuccessCode {
	SUCCESS_JOIN("회원가입에 성공하였습니다.");

	private final String message;

	UserSuccessCode(String message) {
		this.message = message;
	}
}
