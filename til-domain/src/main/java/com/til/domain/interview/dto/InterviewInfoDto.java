package com.til.domain.interview.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record InterviewInfoDto(
                               List<Long> categoryIdList,
                               List<InterviewProblemQuestionDto> problemList
) {

    public static InterviewInfoDto of(List<Long> categoryIdList,
        List<InterviewProblemQuestionDto> problemList) {
        return InterviewInfoDto.builder()
            .categoryIdList(categoryIdList)
            .problemList(problemList)
            .build();
    }

}
