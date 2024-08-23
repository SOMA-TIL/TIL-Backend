package com.til.domain.grading.dto;

import com.til.domain.grading.enums.GradingResult;

import lombok.Builder;

@Builder
public record GradingResultWithProblemInfoDto(
                                              String question,
                                              String userAnswer,
                                              GradingResult result,
                                              String comment
) {

}
