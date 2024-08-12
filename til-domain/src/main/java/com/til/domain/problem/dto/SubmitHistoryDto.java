package com.til.domain.problem.dto;

import java.time.LocalDateTime;

import com.til.domain.grading.enums.GradingResult;

public record SubmitHistoryDto(
                               Long submitId,
                               String answer,
                               GradingResult result,
                               String comment,
                               LocalDateTime submittedDate
) {

}
