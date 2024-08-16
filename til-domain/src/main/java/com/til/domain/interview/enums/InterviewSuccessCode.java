package com.til.domain.interview.enums;

import com.til.domain.common.enums.SuccessCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum InterviewSuccessCode implements SuccessCode {

    SUCCESS_INTERVIEW_CREATION("모의면접 생성에 성공했습니다."),
    SUCCESS_GET_INTERVIEW_INFO("모의면접 상세 정보를 성공적으로 가져왔습니다.");

    private final String message;
}
