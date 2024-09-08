package com.til.controller.problem.response;

import com.til.domain.problem.dto.AdminProblemInfoDto;

import lombok.Builder;

@Builder
public record ProblemInfoResponse(
                                  AdminProblemInfoDto problemInfo
) {

    public static ProblemInfoResponse of(AdminProblemInfoDto adminProblemInfoDto) {
        return new ProblemInfoResponse(adminProblemInfoDto);
    }
}
