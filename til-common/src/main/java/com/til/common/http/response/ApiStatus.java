package com.til.common.http.response;

import com.til.common.http.response.enums.ErrorCode;
import com.til.common.http.response.enums.SuccessCode;

import lombok.Getter;

@Getter
public class ApiStatus {

    private final String code;
    private final String message;

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
