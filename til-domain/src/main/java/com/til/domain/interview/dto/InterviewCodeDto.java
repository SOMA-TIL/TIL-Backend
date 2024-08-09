package com.til.domain.interview.dto;

import com.til.domain.interview.model.Interview;

import lombok.Builder;

@Builder
public record InterviewCodeDto(
                               String code
) {

    public static InterviewCodeDto of(Interview interview) {
        return InterviewCodeDto.builder()
            .code(interview.getCode())
            .build();
    }
}
