package com.til.domain.common.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum BaseSuccessCode implements SuccessCode {

    OK("성공하였습니다.");

    private final String message;
}
