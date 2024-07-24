package com.til.domain.problem.dto;

import com.til.domain.problem.model.Problem;

import lombok.Builder;

@Builder
public record ProblemListDto(
                             String title,
                             Integer score
) {

    public static ProblemListDto of(Problem problem) {
        return ProblemListDto.builder()
            .title(problem.getTitle())
            .score(problem.getScore())
            .build();
    }
}
