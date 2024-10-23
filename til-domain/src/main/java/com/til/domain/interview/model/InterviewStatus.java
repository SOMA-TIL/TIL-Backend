package com.til.domain.interview.model;

public enum InterviewStatus {
    CREATING, // 면접 구성 과정에서 문제 생성 중
    PROCESSING,
    PENDING, // 면접 종료 후 채점 결과 대기중
    DONE,
    ERROR,
    ABORTED
}
