package com.til.controller.problem.response;

import com.til.domain.problem.dto.SolveProblemResultDto;

public record SolveProblemResponse(
                                   SolveProblemResultDto problemResult
) {

    public static SolveProblemResponse of(SolveProblemResultDto solveProblemResultDto) {
        return new SolveProblemResponse(solveProblemResultDto);
    }
}
