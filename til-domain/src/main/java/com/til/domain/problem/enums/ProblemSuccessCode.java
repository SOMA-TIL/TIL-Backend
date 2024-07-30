package com.til.domain.problem.enums;

import com.til.domain.common.enums.SuccessCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProblemSuccessCode implements SuccessCode {

    SUCCESS_GET_PROBLEM_LIST("문제 리스트를 성공적으로 가져왔습니다."),
    SUCCESS_GET_PROBLEM_INFO("문제 상세 정보를 성공적으로 가져왔습니다.");

    private final String message;
}
