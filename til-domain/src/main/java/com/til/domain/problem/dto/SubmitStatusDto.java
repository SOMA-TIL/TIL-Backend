package com.til.domain.problem.dto;

import com.til.domain.grading.enums.GradingStatus;

import lombok.Builder;

@Builder
public record SubmitStatusDto(
                              Long submitId,
                              GradingStatus status
) {

    public static SubmitStatusDto of(Long id, GradingStatus status) {
        return SubmitStatusDto.builder()
            .submitId(id)
            .status(status)
            .build();
    }
}
