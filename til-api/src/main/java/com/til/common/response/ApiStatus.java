package com.til.common.response;

import lombok.Getter;

@Getter
public class ApiStatus {
	private String code;
	private String message;

	private ApiStatus(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public static ApiStatus of(StatusCode status) {
		return new ApiStatus(status.name(), status.getMessage());
	}
}
