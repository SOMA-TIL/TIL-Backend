package com.til.domain.problem.dto;

import com.til.domain.problem.model.ProblemStatus;
import com.til.domain.problem.model.UserProblem;

import lombok.Builder;

@Builder
public record UserProblemResultDto(
                                   ProblemStatus status,
                                   String solution
) {

    public static UserProblemResultDto of(UserProblem userProblem) {
        return UserProblemResultDto.builder()
            .status(userProblem.getStatus())
            .solution(userProblem.getProblem().getSolution())
            .build();
    }
}
