package com.til.domain.interview.dto;

import java.util.List;

import com.til.domain.category.dto.CategoryDto;

import lombok.Builder;

@Builder
public record InterviewInfoDto(
                               Long id,
                               List<CategoryDto> categoryList,
                               List<InterviewProblemQuestionDto> problemList
) {

    public static InterviewInfoDto of(Long interviewId, List<CategoryDto> categoryList,
        List<InterviewProblemQuestionDto> problemList) {
        return InterviewInfoDto.builder()
            .id(interviewId)
            .categoryList(categoryList)
            .problemList(problemList)
            .build();
    }

}
