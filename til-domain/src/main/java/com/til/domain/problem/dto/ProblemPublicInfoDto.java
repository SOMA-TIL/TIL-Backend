package com.til.domain.problem.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record ProblemPublicInfoDto(
                                   Long id,
                                   String title,
                                   String question,
                                   Integer level,
                                   Long finishCount,
                                   Float passRate,
                                   List<Long> categoryList,
                                   boolean isFavorite
) {

    public ProblemPublicInfoDto(Long id, String title, String question, Integer level, Long finishCount, Float passRate,
        List<Long> categoryList) {
        this(id, title, question, level, finishCount, passRate, categoryList, false);
    }

    public ProblemPublicInfoDto setFavorite(boolean isFavorite) {
        return ProblemPublicInfoDto.builder()
            .id(id)
            .title(title)
            .question(question)
            .level(level)
            .finishCount(finishCount)
            .passRate(passRate)
            .categoryList(categoryList)
            .isFavorite(isFavorite)
            .build();
    }
}
