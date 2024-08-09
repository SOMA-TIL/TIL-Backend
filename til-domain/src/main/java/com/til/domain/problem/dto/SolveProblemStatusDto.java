package com.til.domain.problem.dto;

import com.til.domain.grading.enums.GradingStatus;

import lombok.Builder;

@Builder
public record SolveProblemStatusDto(
                                    Long id,
                                    GradingStatus status
) {

    public static SolveProblemStatusDto of(Long id, GradingStatus status) {
        return SolveProblemStatusDto.builder()
            .id(id)
            .status(status)
            .build();
    }
}
