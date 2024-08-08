package com.til.domain.problem.dto;

import com.til.domain.problem.model.ProblemStatus;

import lombok.Builder;

@Builder
public record SolveProblemStatusDto(
                                    ProblemStatus status
) {

    public static SolveProblemStatusDto of(ProblemStatus status) {
        return SolveProblemStatusDto.builder()
            .status(status)
            .build();
    }
}
