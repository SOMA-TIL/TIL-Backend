package com.til.domain.problem.dto;

import java.util.List;

public record SubmitResultDto(
                              List<SubmitHistoryDto> submitHistory,
                              String solution
) {

    public static SubmitResultDto of(List<SubmitHistoryDto> submitHistory, String solution) {
        return new SubmitResultDto(submitHistory, solution);
    }
}
