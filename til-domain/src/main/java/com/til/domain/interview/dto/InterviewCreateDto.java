package com.til.domain.interview.dto;

import java.util.List;

import com.til.domain.interview.model.Interview;
import com.til.domain.interview.model.InterviewStatus;
import com.til.domain.interview.model.InterviewType;

import lombok.Builder;

@Builder
public record InterviewCreateDto(
                                 InterviewType interviewType,
                                 List<Long> categoryIdList,
                                 InterviewStatus status,
                                 String code,
                                 int questionSize,
                                 String portfolio,
                                 Long userId
) {

    public Interview toEntity(String code) {
        return Interview.builder()
            .status(InterviewStatus.CREATING)
            .type(interviewType)
            .code(code)
            .questionSize(questionSize)
            .portfolio(portfolio)
            .userId(userId)
            .build();
    }
}
