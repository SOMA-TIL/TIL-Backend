package com.til.controller.interview.request;

import java.util.List;

import com.til.domain.interview.dto.InterviewCreateDto;

public record InterviewCreateRequest(
                                     List<Long> categoryIdList
) {

    public InterviewCreateDto toServiceDto(Long userId) {
        return InterviewCreateDto.builder()
            .categoryIdList(categoryIdList)
            .userId(userId)
            .build();

    }
}
