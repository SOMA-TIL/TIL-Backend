package com.til.domain.problem.dto;

import com.til.domain.problem.model.ProblemStatus;
import com.til.domain.problem.model.UserProblem;

import lombok.Builder;

@Builder
public record SolveProblemStatusDto(
                                    ProblemStatus status
) {

    public static SolveProblemStatusDto of(UserProblem userProblem) {
        return SolveProblemStatusDto.builder()
            .status(userProblem.getStatus())
            .build();
    }
}
