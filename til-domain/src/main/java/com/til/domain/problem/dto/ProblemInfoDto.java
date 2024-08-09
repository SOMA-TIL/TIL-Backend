package com.til.domain.problem.dto;

import java.util.List;

import com.til.domain.problem.model.Problem;

import lombok.Builder;

@Builder
public record ProblemInfoDto(
                             Long id,
                             String title,
                             String question,
                             String solution,
                             String grading,
                             int level,
                             List<String> categoryList,
                             boolean isFavorite
) {

    public static ProblemInfoDto of(Problem problem) {
        // todo: 카테고리 리스트 받아와서 세팅

        return createBuilderFromProblem(problem)
            .categoryList(null)
            .build();
    }

    public static ProblemInfoDto of(Problem problem, boolean isFavorite) {
        return createBuilderFromProblem(problem)
            .isFavorite(isFavorite)
            .build();
    }

    private static ProblemInfoDtoBuilder createBuilderFromProblem(Problem problem) {
        return ProblemInfoDto.builder()
            .id(problem.getId())
            .title(problem.getTitle())
            .question(problem.getQuestion())
            .solution(problem.getSolution())
            .grading(problem.getGrading())
            .level(problem.getLevel());
    }
}
