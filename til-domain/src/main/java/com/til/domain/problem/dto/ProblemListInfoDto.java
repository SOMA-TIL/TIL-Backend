package com.til.domain.problem.dto;

import java.util.List;

import com.til.domain.problem.model.Problem;

import lombok.Builder;

@Builder
public record ProblemListInfoDto(
                                 Long id,
                                 String title,
                                 int level,
                                 List<String> categoryList
) {

    public static ProblemListInfoDto of(Problem problem) {
        // todo: 카테고리 리스트 받아와서 세팅

        return ProblemListInfoDto.builder()
            .id(problem.getId())
            .title(problem.getTitle())
            .level(problem.getLevel())
            .categoryList(null)
            .build();
    }
}
