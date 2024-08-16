package com.til.domain.interview.enums;

import org.springframework.http.HttpStatus;

import com.til.domain.common.enums.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InterviewErrorCode implements ErrorCode {

    FAIL_CREATE_INTERVIEW(HttpStatus.INTERNAL_SERVER_ERROR, "모의면접 생성에 실패했습니다."),
    ALREADY_PROCESSING_INTERVIEW(HttpStatus.BAD_REQUEST, "이미 진행중인 모의면접이 존재합니다."),
    NOT_FOUND_INTERVIEW(HttpStatus.NOT_FOUND, "존재하지 않는 모의면접입니다."),
    FAIL_GET_INTERVIEW(HttpStatus.BAD_REQUEST, "모의면접 조회에 실패했습니다.");

    private final HttpStatus status;
    private final String message;
}
