package com.til.config.errorhandling;

import com.til.common.response.ApiStatus;
import com.til.common.response.StatusCode;

import lombok.Getter;

@Getter
public class ErrorResponse {
	private ApiStatus status;

	private ErrorResponse(StatusCode statusCode) {
		this.status = ApiStatus.of(statusCode);
	}

	public static ErrorResponse of(StatusCode statusCode) {
		return new ErrorResponse(statusCode);
	}
}
