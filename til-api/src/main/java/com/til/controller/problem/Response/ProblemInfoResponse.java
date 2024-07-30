package com.til.controller.problem.Response;

import com.til.domain.problem.dto.ProblemInfoDto;

import lombok.Builder;

@Builder
public record ProblemInfoResponse(
                                  ProblemInfoDto problemInfo
) {

    public static ProblemInfoResponse of(ProblemInfoDto problemInfoDto) {
        return new ProblemInfoResponse(problemInfoDto);
    }
}
