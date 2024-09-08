package com.til.controller.problem.request;

import java.util.List;

import com.til.domain.problem.dto.AdminUpdateProblemDto;

public record UpdateProblemRequest(
                                   String title,
                                   String question,
                                   String solution,
                                   String grading,
                                   Integer level,
                                   List<Long> categoryIdList
) {

    public AdminUpdateProblemDto toServiceDto(Long id) {
        return AdminUpdateProblemDto.builder()
            .id(id)
            .title(title)
            .question(question)
            .solution(solution)
            .grading(grading)
            .level(level)
            .categoryIdList(categoryIdList)
            .build();
    }
}
