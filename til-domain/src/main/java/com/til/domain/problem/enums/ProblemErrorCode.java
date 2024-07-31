package com.til.domain.problem.enums;

import org.springframework.http.HttpStatus;

import com.til.domain.common.enums.ErrorCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProblemErrorCode implements ErrorCode {

    NOT_FOUND_PROBLEM(HttpStatus.NOT_FOUND, "존재하지 않는 문제입니다."),
    ALREADY_FAVORITE_PROBLEM(HttpStatus.BAD_REQUEST, "이미 즐겨찾기한 문제입니다."),
    NOT_FOUND_FAVORITE_PROBLEM(HttpStatus.BAD_REQUEST, "즐겨찾기한 문제가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
