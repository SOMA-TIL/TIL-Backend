package com.til.controller.interview.request;

import java.util.List;

import com.til.domain.category.dto.CategoryDto;
import com.til.domain.interview.dto.InterviewCreateDto;

public record InterviewCreateRequest(
                                     List<CategoryDto> categoryList,
                                     Long userId
) {

    public InterviewCreateDto toServiceDto(Long userId) {
        return InterviewCreateDto.builder()
            .categoryList(categoryList)
            .userId(userId)
            .build();

    }
}
