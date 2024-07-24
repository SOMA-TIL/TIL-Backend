package com.til.controller.problem.reponse;

import java.util.List;

import com.til.domain.problem.dto.ProblemListDto;

public record ProblemListResponse(
                                  List<ProblemListDto> problemList
) {

    public static ProblemListResponse of(List<ProblemListDto> problemList) {
        return new ProblemListResponse(problemList);
    }
}
