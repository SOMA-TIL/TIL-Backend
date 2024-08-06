package com.til.domain.problem.dto;

import com.til.domain.problem.model.ProblemStatus;
import com.til.domain.problem.model.UserProblem;

import lombok.Builder;

@Builder
public record SolveProblemModalDto(
                                   ProblemStatus status
) {

    public static SolveProblemModalDto of(UserProblem userProblem) {
        return SolveProblemModalDto.builder()
            .status(userProblem.getStatus())
            .build();
    }
}
