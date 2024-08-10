package com.til.domain.interview.enums;

import org.springframework.http.HttpStatus;

import com.til.domain.common.enums.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InterviewErrorCode implements ErrorCode {

    FAIL_CREATE_INTERVIEW(HttpStatus.INTERNAL_SERVER_ERROR, "모의면접 생성에 실패했습니다.");

    private final HttpStatus status;
    private final String message;
}
