package com.til.domain.problem.dto;

import lombok.Builder;

@Builder
public record ProblemUserStatusDto(
                                   boolean isFavorite,
                                   boolean isAttempted,
                                   boolean isPassed
) {

    public static ProblemUserStatusDto of(boolean isFavorite, boolean isAttempted, boolean isPassed) {
        return ProblemUserStatusDto.builder()
            .isFavorite(isFavorite)
            .isAttempted(isAttempted)
            .isPassed(isPassed)
            .build();
    }
}
