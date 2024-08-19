package com.til.domain.problem.dto;

import lombok.Builder;

@Builder
public record ProblemUserStatusDto(
                                   boolean isFavorite,
                                   boolean isPassed
) {

    public static ProblemUserStatusDto of(boolean isFavorite, boolean isPassed) {
        return ProblemUserStatusDto.builder()
            .isFavorite(isFavorite)
            .isPassed(isPassed)
            .build();
    }
}
