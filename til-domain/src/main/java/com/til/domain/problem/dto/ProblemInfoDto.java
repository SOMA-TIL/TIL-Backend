package com.til.domain.problem.dto;

import com.til.domain.problem.model.Problem;

import lombok.Builder;

@Builder
public record ProblemInfoDto(
                             String title,
                             String question,
                             String solution,
                             Integer score
) {

    public static ProblemInfoDto of(Problem problem) {
        return ProblemInfoDto.builder()
            .title(problem.getTitle())
            .question(problem.getQuestion())
            .solution(problem.getSolution())
            .score(problem.getScore())
            .build();
    }
}
