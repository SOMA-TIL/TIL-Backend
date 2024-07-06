package com.til.common.response;

import com.til.domain.common.enums.ErrorCode;
import com.til.domain.common.enums.SuccessCode;

import lombok.Getter;

@Getter
public class ApiStatus {
	private String code;
	private String message;

	private ApiStatus(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static ApiStatus of(SuccessCode status) {
		return new ApiStatus(status.name(), status.getMessage());
	}

	public static ApiStatus of(ErrorCode status) {
		return new ApiStatus(status.name(), status.getMessage());
	}

	public static ApiStatus of(String code, String message) {
		return new ApiStatus(code, message);
	}
}
