package com.til.domain.problem.dto;

import com.til.domain.problem.model.Problem;

import lombok.Builder;

@Builder
public record ProblemDto(
                         String title,
                         String question,
                         String solution,
                         Integer score
) {

    public static ProblemDto of(Problem problem) {
        return ProblemDto.builder()
            .title(problem.getTitle())
            .question(problem.getQuestion())
            .solution(problem.getSolution())
            .score(problem.getScore())
            .build();
    }
}
