package com.til.domain.interview.dto;

import com.til.domain.interview.model.Interview;
import com.til.domain.interview.model.InterviewStatus;

import lombok.Builder;

@Builder
public record SpeechInterviewCreateDto(
                                       int questionSize,
                                       String portfolio,
                                       InterviewStatus status,
                                       String code,
                                       Long userId
) {

    public Interview toEntity(String code) {
        return Interview.builder()
            .status(InterviewStatus.PROCESSING)
            .code(code)
            .userId(userId)
            .build();
    }
}
