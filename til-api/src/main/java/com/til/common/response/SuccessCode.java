package com.til.common.response;

import lombok.Getter;

@Getter
public enum SuccessCode implements StatusCode {
	OK("성공하였습니다."),
	;

	private final String message;

	SuccessCode(String message) {
		this.message = message;
	}
}
