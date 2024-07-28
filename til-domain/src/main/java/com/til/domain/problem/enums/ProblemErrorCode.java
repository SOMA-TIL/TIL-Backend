package com.til.domain.problem.enums;

import org.springframework.http.HttpStatus;

import com.til.domain.common.enums.ErrorCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProblemErrorCode implements ErrorCode {

    INVALID_PAGE_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 페이지 요청입니다.");

    private final HttpStatus status;
    private final String message;
}
