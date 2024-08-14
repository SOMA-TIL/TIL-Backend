package com.til.controller.problem.request;

import java.util.List;

import com.til.domain.problem.dto.ProblemSearchDto;

public record SearchProblemRequest(
                                   String keyword,
                                   Integer level,
                                   List<Long> categoryList
) {

    public ProblemSearchDto toServiceDto() {
        return ProblemSearchDto.builder()
            .keyword(this.keyword)
            .level(this.level)
            .categoryList(this.categoryList)
            .build();
    }
}
