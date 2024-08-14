package com.til.domain.problem.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record ProblemSearchDto(
                               String keyword,
                               Integer level,
                               List<Long> categoryList
) {

}
