package com.til.domain.category.dto;

import com.til.domain.category.model.InterviewCategory;

import lombok.Builder;

@Builder
public record InterviewCategoryDto(
                                   Long interviewId,
                                   Long categoryId
) {

    public static InterviewCategoryDto of(Long interviewId, Long categoryId) {
        return InterviewCategoryDto.builder()
            .interviewId(interviewId)
            .categoryId(categoryId)
            .build();
    }

    public InterviewCategory toEntity() {
        return InterviewCategory.builder()
            .interviewId(interviewId)
            .categoryId(categoryId)
            .build();
    }
}
