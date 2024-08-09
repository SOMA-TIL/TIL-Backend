package com.til.controller.problem.response;

import com.til.domain.grading.dto.GradingResultDto;

public record ProblemResultResponse(
                                    GradingResultDto gradingResult
) {

    public static ProblemResultResponse of(GradingResultDto gradingResultDto) {
        return new ProblemResultResponse(gradingResultDto);
    }
}
