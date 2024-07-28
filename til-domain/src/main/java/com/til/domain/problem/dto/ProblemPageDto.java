package com.til.domain.problem.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.til.domain.problem.model.Problem;

import lombok.Builder;

@Builder
public record ProblemPageDto(
                             List<ProblemListDto> problems,
                             int page,
                             int size,
                             long totalElements,
                             int totalPages
) {

    public static ProblemPageDto of(Page<Problem> problemsPage) {
        List<ProblemListDto> problems = problemsPage.getContent().stream()
            .map(ProblemListDto::of)
            .collect(Collectors.toList());
        return ProblemPageDto.builder()
            .problems(problems)
            .page(problemsPage.getNumber())
            .size(problemsPage.getSize())
            .totalElements(problemsPage.getTotalElements())
            .totalPages(problemsPage.getTotalPages())
            .build();
    }
}
