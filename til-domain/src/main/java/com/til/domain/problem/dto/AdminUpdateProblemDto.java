package com.til.domain.problem.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record AdminUpdateProblemDto(
                                    Long id,
                                    String title,
                                    String question,
                                    String solution,
                                    String grading,
                                    Integer level,
                                    List<Long> categoryIdList
) {
}
