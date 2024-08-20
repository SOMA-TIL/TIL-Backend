package com.til.domain.interview.dto;

import lombok.Builder;

@Builder
public record InterviewSolveDto(
                                String code,
                                Integer sequence,
                                String answer,
                                Long userId
) {
}
