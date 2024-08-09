package com.til.domain.grading.dto;

import com.til.domain.grading.enums.GradingResult;

public record GradingResultDto(
                               GradingResult result,
                               String comment
) {

    public static GradingResultDto of(GradingResult result, String comment) {
        return new GradingResultDto(result, comment);
    }
}
