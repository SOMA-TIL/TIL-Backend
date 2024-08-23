package com.til.domain.grading.dto;

import java.util.List;

import com.til.domain.interview.model.InterviewStatus;

import lombok.Builder;

@Builder
public record InterviewGradingResultDto(
                                        InterviewStatus status,
                                        List<GradingResultWithProblemInfoDto> gradingResult
) {

    public static InterviewGradingResultDto of(InterviewStatus status) {
        return InterviewGradingResultDto.builder()
            .status(status)
            .gradingResult(null)
            .build();
    }

    public static InterviewGradingResultDto of(InterviewStatus status,
        List<GradingResultWithProblemInfoDto> gradingResults) {
        return InterviewGradingResultDto.builder()
            .status(status)
            .gradingResult(gradingResults)
            .build();
    }
}
