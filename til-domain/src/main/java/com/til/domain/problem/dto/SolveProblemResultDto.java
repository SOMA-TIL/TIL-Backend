package com.til.domain.problem.dto;

import com.til.domain.problem.model.ProblemStatus;
import com.til.domain.problem.model.UserProblem;

import lombok.Builder;

@Builder
public record SolveProblemResultDto(
                                    ProblemStatus status,
                                    String solution
) {

    public static SolveProblemResultDto of(UserProblem userProblem) {
        return SolveProblemResultDto.builder()
            .status(userProblem.getStatus())
            .solution(userProblem.getProblem().getSolution())
            .build();
    }
}
