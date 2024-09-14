package com.til.exception;

import com.til.http.response.enums.ErrorCode;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

    private final ErrorCode errorCode;

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
