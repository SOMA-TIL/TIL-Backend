package com.til.domain.interview.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;

@Builder
public record InterviewInfoDto(
                               LocalDateTime createdDate,
                               List<Long> categoryIdList,
                               List<InterviewProblemQuestionDto> problemList
) {

    public static InterviewInfoDto of(LocalDateTime createdDate, List<Long> categoryIdList,
        List<InterviewProblemQuestionDto> problemList) {
        return InterviewInfoDto.builder()
            .createdDate(createdDate)
            .categoryIdList(categoryIdList)
            .problemList(problemList)
            .build();
    }

}
