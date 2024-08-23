package com.til.domain.problem.dto;

import java.util.List;

import com.til.domain.problem.enums.ProblemUserStatus;

import lombok.Builder;

@Builder
public record ProblemSearchDto(
                               String keyword,
                               Integer level,
                               List<Long> categoryList,
                               List<Integer> levelList,
                               ProblemUserStatus status
) {

}
