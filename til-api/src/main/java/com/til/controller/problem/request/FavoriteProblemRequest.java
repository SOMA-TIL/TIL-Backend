package com.til.controller.problem.request;

import com.til.domain.problem.dto.FavoriteProblemDto;

import jakarta.validation.constraints.NotNull;

public record FavoriteProblemRequest(
                                     @NotNull Boolean isFavorite
) {

    public FavoriteProblemDto toServiceDto(Long userId, Long problemId) {
        return FavoriteProblemDto.builder()
            .isFavorite(isFavorite)
            .userId(userId)
            .problemId(problemId)
            .build();
    }
}
