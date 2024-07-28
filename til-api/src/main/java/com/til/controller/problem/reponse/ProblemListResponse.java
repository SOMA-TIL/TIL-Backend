package com.til.controller.problem.reponse;

import java.util.List;

import com.til.domain.problem.dto.ProblemListDto;
import com.til.domain.problem.dto.ProblemPageDto;

import lombok.Builder;

@Builder
public record ProblemListResponse(
                                  List<ProblemListDto> problemList,
                                  int page,
                                  int size,
                                  long totalElements,
                                  int totalPages
) {

    public static ProblemListResponse of(ProblemPageDto problemPageDto) {
        return ProblemListResponse.builder()
            .problemList(problemPageDto.problems())
            .page(problemPageDto.page())
            .size(problemPageDto.size())
            .totalElements(problemPageDto.totalElements())
            .totalPages(problemPageDto.totalPages())
            .build();
    }
}
