package com.til.domain.problem.dto;

import com.til.domain.problem.model.Problem;
import com.til.domain.problem.model.ProblemStatus;
import com.til.domain.problem.model.UserProblem;
import com.til.domain.user.model.User;

import lombok.Builder;

@Builder
public record SolveProblemDto(
                              Long userId,
                              Long problemId,
                              String answer
) {

    public UserProblem toEntity(User user, Problem problem) {
        return UserProblem.builder()
            .user(user)
            .problem(problem)
            .answer(answer)
            .status(ProblemStatus.PENDING)
            .build();
    }
}
