package com.til.controller.interview.request;

import java.util.List;

import com.til.domain.interview.dto.InterviewCreateDto;
import com.til.domain.interview.model.InterviewType;

public record InterviewCreateRequest(
                                     InterviewType interviewType,
                                     List<Long> categoryIdList,
                                     int questionSize,
                                     String portfolio
) {

    public InterviewCreateDto toServiceDto(Long userId) {
        return InterviewCreateDto.builder()
            .interviewType(interviewType)
            .categoryIdList(categoryIdList)
            .questionSize(questionSize)
            .portfolio(portfolio)
            .userId(userId)
            .build();

    }
}
