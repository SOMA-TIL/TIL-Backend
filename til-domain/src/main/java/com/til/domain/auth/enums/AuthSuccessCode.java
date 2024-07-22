package com.til.domain.auth.enums;

import com.til.domain.common.enums.SuccessCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum AuthSuccessCode implements SuccessCode {

    SUCCESS_REISSUE("토큰 재발급에 성공하였습니다.");

    private final String message;
}
