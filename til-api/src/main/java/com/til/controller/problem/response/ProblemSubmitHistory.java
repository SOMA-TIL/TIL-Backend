package com.til.controller.problem.response;

import java.util.List;

import com.til.domain.problem.dto.SubmitHistoryDto;
import com.til.domain.problem.dto.SubmitResultDto;

public record ProblemSubmitHistory(
                                   List<SubmitHistoryDto> submitHistory,
                                   String solution
) {

    public static ProblemSubmitHistory of(SubmitResultDto submitResultDto) {
        return new ProblemSubmitHistory(submitResultDto.submitHistory(), submitResultDto.solution());
    }
}
