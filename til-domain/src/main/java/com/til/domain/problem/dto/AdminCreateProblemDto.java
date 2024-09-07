package com.til.domain.problem.dto;

import com.til.domain.problem.model.Problem;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import java.util.List;

@Builder
public record AdminCreateProblemDto(
    String title,
    String question,
    String solution,
    String grading,
    Integer level,
    List<Long> categoryIdList
) {
    public Problem toEntity() {
        return Problem.builder()
            .title(title)
            .question(question)
            .solution(solution)
            .grading(grading)
            .level(level)
            .build();
    }
}
