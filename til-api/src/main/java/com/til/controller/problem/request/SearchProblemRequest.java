package com.til.controller.problem.request;

import java.util.List;

import com.til.domain.problem.dto.ProblemSearchDto;
import com.til.domain.problem.enums.ProblemUserStatus;

public record SearchProblemRequest(
                                   String keyword,
                                   Integer level,
                                   List<Long> categoryList,
                                   List<Integer> levelList,
                                   ProblemUserStatus status
) {

    public ProblemSearchDto toServiceDto() {
        return ProblemSearchDto.builder()
            .keyword(this.keyword)
            .level(this.level)
            .categoryList(this.categoryList)
            .levelList(this.levelList)
            .status(this.status)
            .build();
    }
}
