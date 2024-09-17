package com.til.common.http.errorhandling;

import com.til.common.http.response.ApiStatus;
import com.til.common.http.response.enums.ErrorCode;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private final ApiStatus status;

    private ErrorResponse(ErrorCode status) {
        this.status = ApiStatus.of(status);
    }

    private ErrorResponse(String code, String message) {
        this.status = ApiStatus.of(code, message);
    }

    public static ErrorResponse of(ErrorCode status) {
        return new ErrorResponse(status);
    }

    public static ErrorResponse of(String code, String message) {
        return new ErrorResponse(code, message);
    }
}
