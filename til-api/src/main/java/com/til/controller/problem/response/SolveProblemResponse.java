package com.til.controller.problem.response;

import com.til.domain.problem.dto.SolveProblemModalDto;

public record SolveProblemResponse(
                                   SolveProblemModalDto problemResult
) {

    public static SolveProblemResponse of(SolveProblemModalDto solveProblemModalDto) {
        return new SolveProblemResponse(solveProblemModalDto);
    }
}
