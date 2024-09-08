package com.til.domain.problem.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record AdminProblemInfoDto(
                                  Long id,
                                  String title,
                                  String question,
                                  Integer level,
                                  Long finishCount,
                                  Float passRate,
                                  List<Long> categoryList
) {
}
