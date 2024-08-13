package com.til.domain.problem.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record ProblemOverviewInfoDto(
                                     Long id,
                                     String title,
                                     Integer level,
                                     List<Long> categoryList
) {

}
