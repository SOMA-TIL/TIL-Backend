package com.til.domain.common.enums;

import lombok.Getter;

@Getter
public enum BaseSuccessCode implements SuccessCode {

    OK("성공하였습니다.");

    private final String message;

    BaseSuccessCode(String message) {
        this.message = message;
    }
}
