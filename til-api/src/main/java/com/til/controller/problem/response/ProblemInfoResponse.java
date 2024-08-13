package com.til.controller.problem.response;

import com.til.domain.problem.dto.ProblemPublicInfoDto;

import lombok.Builder;

@Builder
public record ProblemInfoResponse(
                                  ProblemPublicInfoDto problemInfo
) {

    public static ProblemInfoResponse of(ProblemPublicInfoDto problemPublicInfoDto) {
        return new ProblemInfoResponse(problemPublicInfoDto);
    }
}
