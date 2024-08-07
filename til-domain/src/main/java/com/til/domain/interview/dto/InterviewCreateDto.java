package com.til.domain.interview.dto;

import java.util.List;

import com.til.domain.category.dto.CategoryDto;
import com.til.domain.interview.model.Interview;
import com.til.domain.interview.model.InterviewStatus;

import lombok.Builder;

@Builder
public record InterviewCreateDto(
                                 List<CategoryDto> categoryList,
                                 InterviewStatus status,
                                 String uuid,
                                 Long userId
) {

    public Interview toEntity(String uuid) {
        return Interview.builder()
            .status(InterviewStatus.PROCESSING)
            .uuid(uuid)
            .userId(userId)
            .build();
    }
}
