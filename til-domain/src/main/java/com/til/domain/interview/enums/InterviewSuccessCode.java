package com.til.domain.interview.enums;

import com.til.common.http.response.enums.SuccessCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum InterviewSuccessCode implements SuccessCode {

    SUCCESS_INTERVIEW_CREATION("모의면접 생성에 성공했습니다."),
    SUCCESS_GET_INTERVIEW_INFO("모의면접 상세 정보를 성공적으로 가져왔습니다."),
    SUCCESS_SOLVE_INTERVIEW_PROBLEM("모의면접 문제 답변을 성공적으로 제출했습니다."),
    SUCCESS_SUBMIT_INTERVIEW("모의면접을 성공적으로 제출했습니다."),
    SUCCESS_GET_INTERVIEW_RESULT("모의면접 결과를 성공적으로 가져왔습니다.");

    private final String message;
}
