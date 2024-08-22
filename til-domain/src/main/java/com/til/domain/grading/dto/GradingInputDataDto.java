package com.til.domain.grading.dto;

import lombok.Builder;

@Builder
public record GradingInputDataDto(
                                  String question,
                                  String gradingCriteria,
                                  String userAnswer
) {

    public static GradingInputDataDto of(String question, String gradingCriteria, String userAnswer) {
        return GradingInputDataDto
            .builder()
            .question(question)
            .gradingCriteria(gradingCriteria)
            .userAnswer(userAnswer)
            .build();
    }
}
