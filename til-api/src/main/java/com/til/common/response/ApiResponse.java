package com.til.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
	private ApiStatus status;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T result;

	private ApiResponse(StatusCode statusCode, T result) {
		this.status = ApiStatus.of(statusCode);
		this.result = result;
	}

	public static <T> ApiResponse<T> ok() {
		return new ApiResponse<>(SuccessCode.OK, null);
	}

	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>(SuccessCode.OK, data);
	}

	public static <T> ApiResponse<T> ok(StatusCode statusCode, T data) {
		return new ApiResponse<>(statusCode, data);
	}
}
