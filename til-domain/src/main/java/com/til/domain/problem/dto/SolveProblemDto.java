package com.til.domain.problem.dto;

import com.til.domain.grading.enums.GradingStatus;
import com.til.domain.problem.model.UserProblem;

import lombok.Builder;

@Builder
public record SolveProblemDto(
                              Long userId,
                              Long problemId,
                              String answer
) {

    public UserProblem toEntity() {
        return UserProblem.builder()
            .userId(userId)
            .problemId(problemId)
            .answer(answer)
            .status(GradingStatus.PENDING)
            .build();
    }
}
