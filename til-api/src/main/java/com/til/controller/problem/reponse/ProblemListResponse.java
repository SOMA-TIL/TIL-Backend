package com.til.controller.problem.reponse;

import java.util.List;

import com.til.domain.problem.dto.ProblemInfoDto;

public record ProblemListResponse(
                                  List<ProblemInfoDto> problemInfoDtoList
) {

    public static ProblemListResponse of(List<ProblemInfoDto> problemInfoDtoList) {
        return new ProblemListResponse(problemInfoDtoList);
    }
}
