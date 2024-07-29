package com.til.domain.problem.dto;

import com.til.domain.category.dto.CategoryDto;

import lombok.Builder;

@Builder
public record ProblemInfoDto(
                             Long id,
                             String title,
                             String question,
                             String solution,
                             Integer point,
                             Integer level,
                             Integer solved,
                             Float percentage,
                             CategoryDto category
) {

}
