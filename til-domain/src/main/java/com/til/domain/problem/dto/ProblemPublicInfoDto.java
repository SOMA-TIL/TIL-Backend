package com.til.domain.problem.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record ProblemPublicInfoDto(
                                   Long id,
                                   String title,
                                   String question,
                                   Integer level,
                                   List<Long> categoryList,
                                   boolean isFavorite
) {

    public ProblemPublicInfoDto(Long id, String title, String question, Integer level, List<Long> categoryList) {
        this(id, title, question, level, categoryList, false);
    }

    public ProblemPublicInfoDto setFavorite(boolean isFavorite) {
        return new ProblemPublicInfoDto(id, title, question, level, categoryList, isFavorite);
    }
}
