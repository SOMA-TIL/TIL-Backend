package com.til.controller.problem.response;

import com.til.domain.problem.dto.SolveProblemStatusDto;

public record SolveProblemResponse(
                                   SolveProblemStatusDto problemResult
) {

    public static SolveProblemResponse of(SolveProblemStatusDto solveProblemStatusDto) {
        return new SolveProblemResponse(solveProblemStatusDto);
    }
}
