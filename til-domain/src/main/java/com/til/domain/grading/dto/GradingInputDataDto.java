package com.til.domain.grading.dto;

import lombok.Builder;

@Builder
public record GradingInputDataDto(
                                  String question,
                                  String gradingCriteria,
                                  String userAnswer
) {

}
