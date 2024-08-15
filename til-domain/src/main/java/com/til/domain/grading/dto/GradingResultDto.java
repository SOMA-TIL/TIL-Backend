package com.til.domain.grading.dto;

import com.til.domain.grading.enums.AnswerType;
import com.til.domain.grading.enums.GradingResult;
import com.til.domain.grading.model.Grading;

public record GradingResultDto(
                               GradingResult result,
                               String comment
) {

    public static GradingResultDto of(GradingResult result, String comment) {
        return new GradingResultDto(result, comment);
    }

    public static Grading toEntity(AnswerType type, Long targetId, GradingResultDto gradingResultDto) {
        return Grading.builder()
            .type(type)
            .targetId(targetId)
            .result(gradingResultDto.result())
            .comment(gradingResultDto.comment())
            .build();
    }
}
