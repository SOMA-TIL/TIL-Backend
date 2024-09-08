package com.til.controller.problem.request;

import java.util.List;

import com.til.domain.problem.dto.AdminCreateProblemDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateProblemRequest(
                                   @NotBlank(message = "제목은 필수 항목입니다.") String title,
                                   @NotBlank(message = "답변은 필수 항목입니다.") String question,
                                   @NotBlank(message = "모법 답안은 필수 항목입니다.") String solution,
                                   String grading,
                                   @NotNull(message = "난이도는 필수 항목입니다.") Integer level,
                                   @NotNull(message = "카테고리 ID 리스트는 필수 항목입니다.") List<Long> categoryIdList
) {

    public AdminCreateProblemDto toServiceDto() {
        return AdminCreateProblemDto.builder()
            .title(title)
            .question(question)
            .solution(solution)
            .grading(grading)
            .level(level)
            .categoryIdList(categoryIdList)
            .build();
    }
}
