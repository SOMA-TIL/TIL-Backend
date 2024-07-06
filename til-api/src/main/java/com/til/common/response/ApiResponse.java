package com.til.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.til.domain.common.enums.BaseSuccessCode;
import com.til.domain.common.enums.SuccessCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
	private ApiStatus status;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T result;

	private ApiResponse(SuccessCode status, T result) {
		this.status = ApiStatus.of(status);
		this.result = result;
	}

	public static <T> ApiResponse<T> ok() {
		return new ApiResponse<>(BaseSuccessCode.OK, null);
	}

	public static <T> ApiResponse<T> ok(SuccessCode status) {
		return new ApiResponse<>(status, null);
	}

	public static <T> ApiResponse<T> ok(T data) {
		return new ApiResponse<>(BaseSuccessCode.OK, data);
	}

	public static <T> ApiResponse<T> ok(SuccessCode status, T data) {
		return new ApiResponse<>(status, data);
	}
}
