package com.til.domain.auth.enums;

import org.springframework.http.HttpStatus;

import com.til.domain.common.enums.ErrorCode;

import lombok.Getter;

@Getter
public enum AuthErrorCode implements ErrorCode {

    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("만료된 토큰입니다.");

    private final HttpStatus status;
    private final String message;

    AuthErrorCode(String message) {
        this.status = HttpStatus.UNAUTHORIZED;
        this.message = message;
    }
}
