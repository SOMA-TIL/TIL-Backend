package com.til.domain.problem.dto;

import com.til.domain.problem.model.FavoriteProblem;

import lombok.Builder;

@Builder
public record FavoriteProblemDto(
                                 boolean isFavorite,
                                 Long userId,
                                 Long problemId
) {

    public FavoriteProblem toEntity() {
        return FavoriteProblem.builder()
            .userId(userId)
            .problemId(problemId)
            .build();
    }
}
