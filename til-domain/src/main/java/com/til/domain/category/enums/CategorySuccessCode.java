package com.til.domain.category.enums;

import com.til.domain.common.enums.SuccessCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum CategorySuccessCode implements SuccessCode {

    SUCCESS_GET_CATEGORY_LIST("카테고리 리스트를 성공적으로 가져왔습니다.");

    private final String message;

}
