package com.til.domain.problem.dto;

import com.til.domain.grading.enums.GradingStatus;

import lombok.Builder;

@Builder
public record SolveProblemStatusDto(
                                    GradingStatus status
) {

    public static SolveProblemStatusDto of(GradingStatus status) {
        return SolveProblemStatusDto.builder()
            .status(status)
            .build();
    }
}
