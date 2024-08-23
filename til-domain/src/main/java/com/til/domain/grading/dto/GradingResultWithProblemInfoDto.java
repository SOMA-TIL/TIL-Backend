package com.til.domain.grading.dto;

import com.til.domain.grading.enums.GradingResult;

import lombok.Builder;

@Builder
public record GradingResultWithProblemInfoDto(
                                              String question,
                                              GradingResult result,
                                              String comment
) {

}
