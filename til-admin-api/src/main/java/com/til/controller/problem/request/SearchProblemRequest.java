package com.til.controller.problem.request;

import java.util.List;

import com.til.domain.problem.dto.ProblemSearchDto;

public record SearchProblemRequest(
                                   String keyword,
                                   List<Long> categoryList,
                                   List<Integer> levelList
) {

    public ProblemSearchDto toServiceDto() {
        return ProblemSearchDto.builder()
            .keyword(this.keyword)
            .categoryList(this.categoryList)
            .levelList(this.levelList)
            .build();
    }
}
