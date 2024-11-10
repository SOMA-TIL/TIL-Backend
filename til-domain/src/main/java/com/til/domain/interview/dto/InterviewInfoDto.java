package com.til.domain.interview.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.til.domain.interview.model.InterviewType;

import lombok.Builder;

@Builder
public record InterviewInfoDto(
                               InterviewType type,
                               LocalDateTime createdDate,
                               List<Long> categoryIdList,
                               List<InterviewProblemQuestionDto> problemList
) {

    public static InterviewInfoDto of(InterviewType type, LocalDateTime createdDate, List<Long> categoryIdList,
        List<InterviewProblemQuestionDto> problemList) {
        return InterviewInfoDto.builder()
            .type(type)
            .createdDate(createdDate)
            .categoryIdList(categoryIdList)
            .problemList(problemList)
            .build();
    }

}
