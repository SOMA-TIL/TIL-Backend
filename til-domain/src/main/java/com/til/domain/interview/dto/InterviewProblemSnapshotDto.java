package com.til.domain.interview.dto;

import lombok.Builder;

@Builder
public record InterviewProblemSnapshotDto(
                                          String question,
                                          String gradingCriteria,
                                          Long problemId
) {
}
