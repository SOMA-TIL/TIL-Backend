package com.til.domain.interview.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record InterviewInfoDto(
                               Long id,
                               List<Long> categoryIdList,
                               List<InterviewProblemQuestionDto> problemList
) {

    public static InterviewInfoDto of(Long interviewId, List<Long> categoryIdList,
        List<InterviewProblemQuestionDto> problemList) {
        return InterviewInfoDto.builder()
            .id(interviewId)
            .categoryIdList(categoryIdList)
            .problemList(problemList)
            .build();
    }

}
