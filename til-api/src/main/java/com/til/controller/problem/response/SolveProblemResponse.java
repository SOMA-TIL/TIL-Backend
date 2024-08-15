package com.til.controller.problem.response;

import com.til.domain.problem.dto.SubmitStatusDto;

public record SolveProblemResponse(
                                   SubmitStatusDto submitInfo
) {

    public static SolveProblemResponse of(SubmitStatusDto submitStatusDto) {
        return new SolveProblemResponse(submitStatusDto);
    }
}
